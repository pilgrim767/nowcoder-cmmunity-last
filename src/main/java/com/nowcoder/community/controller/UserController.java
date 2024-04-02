package com.nowcoder.community.controller;

import com.nowcoder.community.annotation.LoginRequired;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.LikeService;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    // 发生问题的时候都要记日志
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    // 注入上传路径
    @Value("${community.path.upload}")
    private String uploadPath;

    // 注入域名
    @Value("${community.path.domain}")
    private String domain;

    // 注入项目访问路径
    @Value("${server.servlet.context-path}")
    private String contextPath;

    // 注入用户服务
    @Autowired
    private UserService userService;

    // 注入当前用户
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    // 获取账号设置页面
    @LoginRequired
    @RequestMapping(path = "/setting", method = RequestMethod.GET)
    public String getSettingPage() {
        return "/site/setting";
    }

    // 处理上传文件的请求【即：上传头像】
    // 上传的时候表单的请求必须是post
    // uploadHeader方法处理这个请求
    // MultipartFile是spring自带的
    // Model给模板携带数据
    @LoginRequired
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model) {
        // 判断传入的参数是否存在问题
        // 存在问题，回到上传的页面，提示问题
        if (headerImage == null) {
            model.addAttribute("error", "您还没有选择图片!");
            return "/site/setting";
        }

        // 获取用户上传的文件原始文件名
        String fileName = headerImage.getOriginalFilename();
        // 截取文件名的后缀，从最后一个点的索引往后截取
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        // 判断上传的文件后缀是否错误
        if (StringUtils.isBlank(suffix)) {
            model.addAttribute("error", "文件的格式不正确!");
            return "/site/setting";
        }

        // 生成随机文件名，随机字符串+后缀
        fileName = CommunityUtil.generateUUID() + suffix;
        // 确定文件存放的路径
        // 配置的文件上传路径：d:/WorkSpace/data/upload
        // 根据配置的上传路径生成一个文件
        File dest = new File(uploadPath + "/" + fileName);
        try {
            // 存储文件
            // 将当前文件内容存入目标文件中去
            headerImage.transferTo(dest);
        } catch (IOException e) {
            logger.error("上传文件失败: " + e.getMessage());
            throw new RuntimeException("上传文件失败,服务器发生异常!", e);
        }

        // 更新当前用户的头像的路径(web访问路径)
        // http://localhost:8080/community/user/header/xxx.png
        // 获取当前用户
        User user = hostHolder.getUser();
        String headerUrl = domain + contextPath + "/user/header/" + fileName;
        userService.updateHeader(user.getId(), headerUrl);

        // 回到首页
        return "redirect:/index";
    }

    // 获取头像
    // 浏览器响应的是一个二进制图像，是一个流
    @RequestMapping(path = "/header/{fileName}", method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        // 服务器存放路径
        fileName = uploadPath + "/" + fileName;
        // 文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        // 响应图片
        response.setContentType("image/" + suffix);
        try (
                // try-with-source方法， 编译时自动生成finally块关闭流
                // 读文件filename获取输入流
                FileInputStream fis = new FileInputStream(fileName);
                // 输出流
                OutputStream os = response.getOutputStream();
        ) {
            // 输出的缓冲区，每次读取1024个字节
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }
        } catch (IOException e) {
            logger.error("读取头像失败: " + e.getMessage());
        }
    }

    // 修改密码
    @RequestMapping(path = "/updatePassword", method = RequestMethod.POST)
    public String updatePassword(String oldPassword, String newPassword, Model model) {
        User user = hostHolder.getUser();
        Map<String, Object> map = userService.updatePassword(user.getId(), oldPassword, newPassword);
        if (map == null || map.isEmpty()) {
            return "redirect:/logout";
        } else {
            model.addAttribute("oldPasswordMsg", map.get("oldPasswordMsg"));
            model.addAttribute("newPasswordMsg", map.get("newPasswordMsg"));
            return "/site/setting";
        }
    }

    // 个人主页
    @RequestMapping(path = "/profile/{userId}", method = RequestMethod.GET)
    public String getProfilePage(@PathVariable("userId") int userId, Model model) {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("该用户不存在!");
        }

        // 用户
        model.addAttribute("user", user);
        // 点赞数量
        int likeCount = likeService.findUserLikeCount(userId);
        model.addAttribute("likeCount", likeCount);

        return "/site/profile";
    }



}

