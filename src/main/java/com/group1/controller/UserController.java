package com.group1.controller;

import com.group1.Dao.MusiccolRepository;
import com.group1.Dao.TagRepository;
import com.group1.entity.*;
import com.group1.service.CommentService;
import com.group1.service.MusicService;
import com.group1.service.UserService;
import com.group1.service.UserlogService;
import com.group1.util.Campare;
import com.group1.util.EdgeIEPath;
import com.group1.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;



@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private UserlogService userlogService;
    @Autowired
    private MusicService musicService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private MusiccolRepository musiccolRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private EdgeIEPath edgeIEPath;


    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    @GetMapping("/index")
    public String index(@RequestParam(value = "tid", required = false) String tid,
                        @RequestParam(value = "cm", required = false) String cm,
                        @RequestParam(value = "pm", required = false) String pm,
                        Model map) {
        List<Music> muiscs = musicService.getallcheckedmusic();
        List<Tag> tags = tagRepository.findAll();
        /*
        for (Tag tag : tags)
            System.out.println(tag);

         */
        if (null != tid && !tid.equals("")) {
            muiscs = musicService.getALLcheckedmusicByTag_id(Integer.parseInt(tid));
        } else
            tid = "";
        if (null != pm && !pm.equals("")) {
            if (0 == Integer.parseInt(pm))
                muiscs.sort(Comparator.comparingLong(Music::getPlay_num));
            else
                muiscs.sort(Comparator.comparingLong(Music::getPlay_num).reversed());
        } else pm = "";
        if (null != cm && !cm.equals("")) {
            if (0 == Integer.parseInt(cm))
                muiscs.sort(Comparator.comparingLong(Music::getComment_num));
            else
                muiscs.sort(Comparator.comparingLong(Music::getComment_num).reversed());
        } else cm = "";
        map.addAttribute("musics", muiscs);
        map.addAttribute("tags", tags);
        map.addAttribute("tid", tid);
        map.addAttribute("cm", cm);
        map.addAttribute("pm", pm);
        return "user/index";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes,
                        HttpServletRequest request
    ) {
        String ipAddress = IpUtil.getIpAddr(request);
        User user = userService.checkUser(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            Userlog userlog = new Userlog();
            userlog.setUser(user);
            userlog.setIp(ipAddress);
            userlog.setAdd_time(new Date());
            userlogService.saveUserlog(userlog);
            return "redirect:/user/index";
        } else {
            attributes.addFlashAttribute("message", "用户名和密码错误,请重新尝试！");
            return "redirect:/user/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/user/login";
    }

    @GetMapping("/user")
    public String userPage(ModelMap map, HttpSession session) {
        User user = (User) session.getAttribute("user");
        String username = "None";
        map.addAttribute("user", user);
        return "user/user";
    }

    @PostMapping("/user")
    public String user(@RequestParam String name,
                       @RequestParam String email,
                       @RequestParam String phone,
                       @RequestParam MultipartFile face,
                       @RequestParam String info,
                       HttpServletRequest request,
                       HttpSession session,
                       RedirectAttributes attributes) throws IOException {
        boolean flag = true;
        User user = (User) session.getAttribute("user");
        if (!userService.isFindUserByUsername(name) && !name.equals(user.getName())) {
            attributes.addFlashAttribute("message", "该用户名被已注册");
            flag = false;
        }
        if (!userService.isFindUserByPhone(phone) && !phone.equals(user.getPhone())) {
            attributes.addFlashAttribute("message", "该手机号被已注册");
            flag = false;
        }
        if (!userService.isFindUserByEmail(email) && !email.equals((user.getEmail()))) {
            attributes.addFlashAttribute("message", "该邮箱已被注册");
            flag = false;
        }
        String filename = edgeIEPath.pathChange(face.getOriginalFilename());
        if (!face.isEmpty()) {
            // 构建上传文件的存放路径
            String path = ResourceUtils.getURL("classpath:static").getPath();
//            String path = request.getServletContext().getRealPath("/static/users/face/");
            //System.out.println("path = " + path);

            // 获取上传的文件名称，并结合存放路径，构建新的文件名称
            String suffixName = filename.substring(filename.lastIndexOf("."));
            //System.out.println(suffixName);
            if (!suffixName.equals(".jpg") && !suffixName.equals(".png")) {
                attributes.addFlashAttribute("message", "图片格式只能是jpg或png！");
                flag = false;
            } else {
                File filepath = new File(path + "/users/face/", filename);
                //System.out.println(filepath);
                if (!filepath.getParentFile().exists()) {
                    filepath.getParentFile().mkdirs();
                }
                // 将上传文件保存到目标文件目录
                face.transferTo(filepath);
            }
        }
        if (flag) {
            user.setName(name);
            user.setEmail(email);
            user.setPhone(phone);
            user.setInfo(info);
            user.setHeadresaddr(filename);
            userService.saveUser(user);
        }
        return "redirect:/user/user";
    }

    @GetMapping("/recommend")
    public String recommend(@RequestParam(value = "id", required = false) Integer musicID, ModelMap map, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (musiccolRepository.findAllByUser(user.getId()).isEmpty()) {
            List<Music> musicList = musicService.getSortedMusic();
            musicService.deleteUncheckMusic(musicList);
            map.addAttribute("musicList", musicList);
        } else {
            List<Integer> allColUser = musiccolRepository.getAllUser();
            List<Integer> allColMusic = musiccolRepository.getAllMusic();
            System.out.println(allColMusic.size());
            int[][] CFMatrix = new int[allColUser.size()][allColMusic.size()];
            for (int i = 0; i < allColUser.size(); i++) {
                for (int j = 0; j < allColMusic.size(); j++) {
                    if (musiccolRepository.findMusiccol(allColUser.get(i), allColMusic.get(j)) != null)
                        CFMatrix[i][j] = 1;
                    else
                        CFMatrix[i][j] = 0;
                }
            }
            int UserIndex = allColUser.indexOf(user.getId());
            ArrayList<Double> W = new ArrayList<>();
            for (int i = 0; i < allColUser.size(); i++) {
                if (allColUser.get(i) != user.getId()) {
                    W.add(Campare.campare(CFMatrix[i], CFMatrix[UserIndex], allColMusic.size()));
                } else
                    W.add(0.0);
            }
            int indexOfMax = W.indexOf(Collections.max(W));
            List<Integer> mID = musiccolRepository.findByUID(allColUser.get(indexOfMax));
            List<Music> musicList = new ArrayList<>();
            Iterator<Integer> it = mID.iterator();
            while (it.hasNext()) {
                musicList.add(musicService.findMusicById(it.next()));
            }
            //去除未检验的音乐
            musicService.deleteUncheckMusic(musicList);
            map.addAttribute("musicList", musicList);
        }
        return "user/recommend";
    }

    @PostMapping("/uploadmusic")
    public String upload(@RequestParam String name,
                         @RequestParam String zuozhe,
                         @RequestParam MultipartFile image,
                         @RequestParam MultipartFile music,
                         @RequestParam MultipartFile video,
                         HttpServletRequest request,
                         HttpSession session,
                         RedirectAttributes attributes) throws IOException {
        boolean flag = true;
        System.out.println(image.isEmpty());
        System.out.println(music.isEmpty());
        System.out.println(video.isEmpty());
        if (!image.isEmpty() && !video.isEmpty() && !music.isEmpty()) {
            // 构建上传文件的存放路径
            String filename = edgeIEPath.pathChange(image.getOriginalFilename());
            String filename1 = edgeIEPath.pathChange(video.getOriginalFilename());
            String filename2 = edgeIEPath.pathChange(music.getOriginalFilename());
            //System.out.println("路径：" + System.getProperty("user.dir"));

            String path = ResourceUtils.getURL("classpath:static").getPath();
            // 获取上传的文件名称，并结合存放路径，构建新的文件名称
            String suffixName = filename.substring(filename.lastIndexOf("."));
            String suffixName1 = filename1.substring(filename1.lastIndexOf("."));
            String suffixName2 = filename2.substring(filename2.lastIndexOf("."));
            System.out.println(suffixName);
            System.out.println(suffixName1);
            System.out.println(suffixName2);

            if (!suffixName.equals(".jpg") && !suffixName.equals(".png")) {
                attributes.addFlashAttribute("message", "图片格式只能是jpg或png！");
                flag = false;
            } else if (!suffixName1.equals(".mp4") && !suffixName1.equals(".mkv")) {
                attributes.addFlashAttribute("message", "视频格式只能是mp4或mkv！");
                flag = false;
            } else if (!suffixName2.equals(".mp3") && !suffixName2.equals(".flac")) {
                attributes.addFlashAttribute("message", "视频格式只能是flac或mp3！");
                flag = false;

            } else {
                File filepath = new File(path + "/users/music/images/", filename);
                File filepath1 = new File(path + "/users/music/mvs/", filename1);
                File filepath2 = new File(path + "/users/music/songs/", filename2);
                System.out.println(filepath);
                if (!filepath.getParentFile().exists()) {
                    filepath.getParentFile().mkdirs();
                }
                // 将上传文件保存到目标文件目录
                image.transferTo(filepath);
                video.transferTo(filepath1);
                music.transferTo(filepath2);
            }
            if (flag) {
                Music music1 = new Music();
                music1.setIscheckd(false);
                music1.setImgresaddr(filename);
                music1.setMvresaddr(filename1);
                music1.setResaddr(filename2);
                music1.setInfo("暂无");
                music1.setName(name);
                music1.setZuozhe(zuozhe);
                musicService.saveMusic(music1);
                attributes.addFlashAttribute("ok", "提交成功");
            }
        }
        return "redirect:/user/uploadmusic";
    }

    @GetMapping("/search")
    public String searchPage(@RequestParam(name = "key") String key, Model map) {
        System.out.println(key);
        List<Music> musicList = musicService.searchMusic(key);
        map.addAttribute("musicList", musicList);
        map.addAttribute("key", key);
        map.addAttribute("count", musicList.size());
        return "user/search";

    }

    @GetMapping("/pwd")
    public String pwdPage() {
        return "user/pwd";
    }

    @PostMapping("/pwd")
    public String pwd(@RequestParam String oldpwd, @RequestParam String newpwd, HttpSession session, RedirectAttributes attributes) {
        User user = (User) session.getAttribute("user");
        if (userService.isRightPwd(user, oldpwd)) {
            userService.updatePwd(user, newpwd);
            attributes.addFlashAttribute("p_message", "修改成功！");
        } else {
            attributes.addFlashAttribute("n_message", "密码错误,请重新尝试！");
        }
        return "redirect:/user/pwd";
    }


    @GetMapping("/playmusic/{id}")
    public String playmusicPage(@PathVariable("id") Integer id, ModelMap map) {

        Music music = musicService.findMusicById(id);

        map.addAttribute("music", music);

        return "user/play_music";
    }

    @GetMapping("/comment")
    public String commentPage(ModelMap map, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Comment> comments = userService.queryComment(user.getId());
        map.addAttribute("comments", comments);
        map.addAttribute("user", user);
        return "user/comments";
    }

    @GetMapping("loginlog")
    public String loginlogPage(ModelMap map, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Userlog> userlogs = userlogService.queryUserlog(user.getId());
        map.addAttribute("userlogs", userlogs);
        return "user/loginlog";
    }

    @GetMapping("uploadmusic")
    public String uploadmusicPage() {
        return "user/uploadmusic";
    }


    @GetMapping("musiccol/add")
    @ResponseBody
    public String addmusiccol(@RequestParam(name = "uid") int uid, @RequestParam(name = "mid") int mid) {
        //msg m = new msg();
        Musiccol musiccol = musiccolRepository.findMusiccol(uid, mid);
        if (musiccol != null) {
            return "0";
        } else {
            User user = userService.findUserById(uid);
            Music music = musicService.findMusicById(mid);
            musiccol = new Musiccol();
            musiccol.setUser(user);
            musiccol.setMusic(music);
            musiccolRepository.save(musiccol);
            //m.setOk("1");
            return "1";
        }
    }

    @GetMapping("musiccol")
    public String musiccolPage(ModelMap map, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Musiccol> musiccols = musiccolRepository.findAllByUser(user.getId());
        map.addAttribute("musiccols", musiccols);
        return "user/musiccol";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "user/register";
    }

    @GetMapping("/play/{id}")
    public String playPage(@PathVariable("id") Integer id, ModelMap map, HttpSession session) {
        Music music = musicService.findMusicById(id);
        User user = (User) session.getAttribute("user");
        List<Comment> comments = commentService.findCommentsByMusic_Id(id);
        map.addAttribute("music", music);
        map.addAttribute("comments", comments);
        map.addAttribute("user_id", user.getId());
        music.setPlay_num(music.getPlay_num() + 1);
        musicService.saveMusic(music);

        return "user/play";
    }

    @PostMapping("/play/{id}")
    public String play(@PathVariable("id") Integer id, ModelMap map,
                       RedirectAttributes attributes, @RequestParam String comment,
                       HttpSession session) {
        if (comment.equals(""))
            attributes.addFlashAttribute("error", "请输入字符!");
        else {
            attributes.addFlashAttribute("ok", "添加评论成功!");
            Comment new_comment = new Comment();
            new_comment.setContent(comment);
            Music music = musicService.findMusicById(id);
            User user = (User) session.getAttribute("user");
            new_comment.setMusic(music);
            new_comment.setUser(user);
            commentService.saveComment(new_comment);
            music.setComment_num(music.getComment_num() + 1);
            musicService.saveMusic(music);
        }

        return "redirect:/user/play/" + id;
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String gender,
                           @RequestParam String password,
                           @RequestParam String phone,
                           @RequestParam String email,
                           RedirectAttributes attributes) {
        boolean flag = true;
        //System.out.println("**********************");
        if (!userService.isFindUserByUsername(username)) {
            attributes.addFlashAttribute("message", "该用户名已被注册");
            System.out.println("------------------------- ------------------");
            flag = false;
        }
        if (!userService.isFindUserByPhone(phone)) {
            attributes.addFlashAttribute("message", "该手机号被已注册");
            flag = false;
        }
        if (!userService.isFindUserByEmail(email)) {
            attributes.addFlashAttribute("message", "该邮箱已被注册");
            flag = false;
        }
        if (flag) {
            User user = new User();
            user.setName(username);
            user.setXb(gender);
            user.setPwd(password);
            user.setPhone(phone);
            user.setEmail(email);
            userService.addUser(user);
            return "user/index";
        } else return "redirect:/user/register";
    }

}
