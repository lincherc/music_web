package com.group9.musicweb.controller;

import com.group9.musicweb.Dao.CommentRepository;
import com.group9.musicweb.Dao.TagRepository;
import com.group9.musicweb.entity.Admin;
import com.group9.musicweb.entity.Tag;
import com.group9.musicweb.service.*;
import com.group9.musicweb.util.ResultRequest;
import com.group9.musicweb.util.TableData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private MusicService musicService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserlogService userlogService;

    @Autowired
    private MusiccolService musiccolService;

    /**
     * 管理员登陆界面
     *
     * @return
     */
    @RequestMapping("/login")
    public String loginPage() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(String nickname, String pwd, RedirectAttributes attributes, HttpSession session) {
        Admin admin = adminService.checkAdmin(nickname, pwd);
        if (admin != null) {
            session.setAttribute("admin", admin);
            return "redirect:/admin/index";
        } else {
            attributes.addFlashAttribute("message", "用户名和密码错误,请重新尝试！");
            return "redirect:/admin/login";
        }

    }

    @RequestMapping("/index")
    public String index(ModelMap map, HttpSession session) {
        Admin admin = (Admin) session.getAttribute("admin");
        map.addAttribute("admin", admin);
        return "admin/index";
    }

    @RequestMapping("/musicAdmin")
    public String musicAdmin(ModelMap map) {
        // 传入标签
        List<Tag> tagList = tagService.queryAllTag();
        map.put("tagList", tagList);
        return "admin/musicAdmin";
    }

    @RequestMapping("/musicDelete")
    public String musicDelete(ModelMap map) {
        return "admin/musicDelete";
    }

    @RequestMapping("/userLoginlog")
    public String userLoginlog(ModelMap map) {
        return "admin/userLoginlog";
    }

    @RequestMapping("/userMusiccol")
    public String userMusiccol(ModelMap map) {
        return "admin/userMusiccol";
    }

    @RequestMapping(value = "queryCheckedMusicList")
    @ResponseBody
    public TableData queryCheckedMusicList(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        String ps = Objects.toString(params.get("pageSize"));
        String pn = Objects.toString(params.get("pageNumber"));
        TableData data = new TableData();
        try {
            if (StringUtils.isNotBlank(ps) && StringUtils.isNotBlank(ps)) {
                Integer pageSize = Integer.valueOf(ps);
                Integer pageNumber = Integer.valueOf(pn);
                String name = null;
                if (params.get("name") != null || params.get("name") != "") {
                    name = Objects.toString(params.get("name"));
                }
                data = musicService.getCheckedMusic(name, pageSize, pageNumber);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    @RequestMapping(value = "queryMusicList")
    @ResponseBody
    public TableData queryMusicList(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        String ps = Objects.toString(params.get("pageSize"));
        String pn = Objects.toString(params.get("pageNumber"));
        TableData data = new TableData();
        try {
            if (StringUtils.isNotBlank(ps) && StringUtils.isNotBlank(ps)) {
                Integer pageSize = Integer.valueOf(ps);
                Integer pageNumber = Integer.valueOf(pn);
                String name = null;
                if (params.get("name") != null || params.get("name") != "") {
                    name = Objects.toString(params.get("name"));
                }
                data = musicService.getUnCheckedMusic(name, pageSize, pageNumber);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    @RequestMapping(value = "checkmusic")
    @ResponseBody
    public ResultRequest checkmusic(Integer tagId, Integer id, String info) {
        ResultRequest result = new ResultRequest();
        System.out.println(tagId);
        System.out.println(id);
        System.out.println(info);

        try {
            if (!Objects.isNull(tagId) && !Objects.isNull(id)) {
                result = musicService.checkedPass(tagId, id, info);
            }
        } catch (Exception e) {
            result.setMessage("保存失败");
            result.setState(false);
        }
        return result;
    }

    @RequestMapping(value = "deletemusic")
    @ResponseBody
    public ResultRequest deletmusic(Integer id) {
        ResultRequest result = new ResultRequest();
        System.out.println("********************");
        System.out.println(id);
        try {
            if (!Objects.isNull(id)) {
                result = musicService.deleteMusic(id);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result.setMessage("删除失败");
            result.setState(false);
        }
        return result;
    }

    @RequestMapping("/commentDelete")
    public String commentDelete(Model map) {
        return "admin/commentDelete";
    }


    @RequestMapping(value = "queryCommentList")
    @ResponseBody
    public TableData queryCommentList(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        String ps = Objects.toString(params.get("pageSize"));
        String pn = Objects.toString(params.get("pageNumber"));
        TableData data = new TableData();
        try {
            if (StringUtils.isNotBlank(ps) && StringUtils.isNotBlank(ps)) {
                Integer pageSize = Integer.valueOf(ps);
                Integer pageNumber = Integer.valueOf(pn);
                String name = null;
                if (params.get("name") != null || params.get("name") != "") {
                    name = Objects.toString(params.get("name"));
                }
                data = commentService.getallComment(name, pageSize, pageNumber);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    @RequestMapping(value = "deletecomment")
    @ResponseBody
    public ResultRequest deletecomment(Integer id) {
        ResultRequest result = new ResultRequest();
//        System.out.println("********************");
        System.out.println(id);
        try {
            if (!Objects.isNull(id)) {
                System.out.println("hello,world!");
                commentRepository.deleteById(id);
                result.setState(true);
                result.setMessage("删除成功！");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result.setMessage("删除失败");
            result.setState(false);
        }
        return result;
    }

    @RequestMapping("/tagControl")
    public String tagControl(ModelMap map) {
        // 传入标签
        return "admin/tagControl";
    }


    @RequestMapping(value = "deletetag")
    @ResponseBody
    public ResultRequest deletetag(Integer id) {
        ResultRequest result = new ResultRequest();
        System.out.println(id);
        try {
            if (!Objects.isNull(id)) {
                tagRepository.deleteById(id);
                result.setState(true);
                result.setMessage("删除成功！");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result.setMessage("删除失败");
            result.setState(false);
        }
        return result;
    }

    @RequestMapping(value = "addtag")
    @ResponseBody
    public ResultRequest addtag(String name) {
        ResultRequest result = new ResultRequest();
        System.out.println(name);
        try {
            if (!Objects.isNull(name) && !name.equals("")) {
                Tag tag = new Tag();
                tag.setTagName(name);
                tag.setAddtime(new Date());
                tagRepository.save(tag);
                result.setState(true);
                result.setMessage("添加成功！");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result.setMessage("添加失败");
            result.setState(false);
        }
        return result;
    }


    @RequestMapping(value = "queryTagList")
    @ResponseBody
    public TableData queryTagList(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        String ps = Objects.toString(params.get("pageSize"));
        String pn = Objects.toString(params.get("pageNumber"));
        TableData data = new TableData();
        try {
            if (StringUtils.isNotBlank(ps) && StringUtils.isNotBlank(ps)) {
                Integer pageSize = Integer.valueOf(ps);
                Integer pageNumber = Integer.valueOf(pn);
                String name = null;
                if (params.get("name") != null || params.get("name") != "") {
                    name = Objects.toString(params.get("name"));
                }
                data = tagService.getAllTag(name, pageSize, pageNumber);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    @RequestMapping(value = "queryLoginlogList")
    @ResponseBody
    public TableData queryLoginlogList(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        String ps = Objects.toString(params.get("pageSize"));
        String pn = Objects.toString(params.get("pageNumber"));
        TableData data = new TableData();
        try {
            if (StringUtils.isNotBlank(ps) && StringUtils.isNotBlank(ps)) {
                Integer pageSize = Integer.valueOf(ps);
                Integer pageNumber = Integer.valueOf(pn);
                String name = null;
                if (params.get("name") != null || params.get("name") != "") {
                    name = Objects.toString(params.get("name"));
                }
                data = userlogService.getallUserlog(name, pageSize, pageNumber);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    @RequestMapping(value = "queryMusiccolList")
    @ResponseBody
    public TableData queryMusiccolList(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        String ps = Objects.toString(params.get("pageSize"));
        String pn = Objects.toString(params.get("pageNumber"));
        TableData data = new TableData();
        try {
            if (StringUtils.isNotBlank(ps) && StringUtils.isNotBlank(ps)) {
                Integer pageSize = Integer.valueOf(ps);
                Integer pageNumber = Integer.valueOf(pn);
                String name = null;
                if (params.get("name") != null || params.get("name") != "") {
                    name = Objects.toString(params.get("name"));
                }
                data = musiccolService.getallMusiccol(name, pageSize, pageNumber);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data;
    }


}
