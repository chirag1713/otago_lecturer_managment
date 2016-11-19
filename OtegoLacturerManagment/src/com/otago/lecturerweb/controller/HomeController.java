package com.otago.lecturerweb.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.otago.lecturercommon.entity.CourseMapping;
import com.otago.lecturercommon.entity.LearningOutcome;
import com.otago.lecturercommon.entity.User;
import com.otago.lecturerweb.Dao.CourseDao;
import com.otago.lecturerweb.utill.CommonUtil;

@Controller
public class HomeController {
    private static Logger logger = Logger.getLogger(HomeController.class);

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private CourseDao courseDao;

    @RequestMapping(value = "/")
    public String geHomePage(HttpServletRequest request) {
        try {

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return "home";
    }

    @RequestMapping(value = "/course")
    public String getCoursePage(HttpServletRequest request) {
        User user = null;
        try {
            user = commonUtil.getUser(request);
            if (user == null) {
                throw new Exception("Please login first.");
            }
            List<CourseMapping> courseMappingList = courseDao.getUserCourses(user.getId());
            request.setAttribute("courseMappingList", courseMappingList);

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return "courses";
    }

    @RequestMapping("/getlearningoutcome/{courseid}")
    public String getLearningOutcome(@PathVariable("courseid") int courseId, HttpServletRequest request) {
        try {
            User user = commonUtil.getUser(request);
            if (user == null) {
                throw new Exception("User Not Found");
            }
            List<LearningOutcome> learningOutcomeList = courseDao.getLearningOutcome(courseId);

            request.setAttribute("learningOutcomeList", learningOutcomeList);
        } catch (Exception e) {
            logger.error("Error occured while getUserOrder" + e.getMessage());
        }
        return "LearningOutcomeAndCapabilities";
    }
}
