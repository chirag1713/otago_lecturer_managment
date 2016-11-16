package com.otago.lecturerweb.utill;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otago.lecturercommon.entity.AuthUser;
import com.otago.lecturercommon.entity.Status;
import com.otago.lecturercommon.entity.User;
import com.otago.lecturerweb.Dao.AuthDao;

@Service
public class AuthValidationUtill {
    private static org.apache.log4j.Logger log = Logger.getLogger(CommonUtil.class);

    @Autowired
    private AuthDao authDao;

    public boolean mobileValidate(String mobile) {
        if (mobile == null || mobile.equals("") || !mobile.matches("^[0-9]{10}+$") || mobile.length() != 10) {
            return false;
        }
        return true;
    }

    public boolean passwordValidate(String password) {
        if (password != null && !password.isEmpty()) {
            // Pattern p =
            // Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{6,32}");
            // Matcher m = p.matcher(password);
            // if (!m.matches()) {
            // return false;
            // }
            if (password.length() < 6 || password.length() > 32) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    public boolean emailValidate(String email) {
        if (email != null && !email.isEmpty()) {
            Pattern p = Pattern.compile("^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$");
            Matcher m = p.matcher(email);
            if (!m.matches()) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    public String getRequestUrlWithQueryString(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        if (request.getQueryString() != null) {
            if (requestURL.indexOf("?") < 0) {
                requestURL.append("?").append(request.getQueryString());
            }
        }
        return requestURL.toString();
    }

    public String parserForSeoUrl(String content, Map<String, Object> map) {
        return parser(content, map, "", true, 0);
    }

    public String parser(String content, Map<String, Object> map, String prefix, boolean seoUrl, int alertType) {
        if (content != null && map != null && !content.trim().isEmpty()) {
            for (String key : map.keySet()) {
                if (map.get(key) instanceof Map) {
                    content = parser(content, (Map<String, Object>) map.get(key), prefix + key + ".", seoUrl, alertType);
                    continue;
                }
                String value = map.get(key) + "";
                if (map.get(key) == null) {
                    value = "";
                }
                if (seoUrl) {
                    value = replaceSpecialCharter(value);
                }
                content = content.replaceAll("\\{" + prefix + key + "\\}", value);
            }
            if (prefix != null && prefix.isEmpty()) {
                content = content.replaceAll("\\{.*?\\}", "");
            }
        }
        return content;
    }

    public String replaceSpecialCharter(String Name) {
        return Name.replaceAll("\\/+", "").replaceAll("[^\\w]", " ").replaceAll(" +", " ").trim().replaceAll("[^\\w]", "-").toLowerCase();
    }

    public String implodeString(List<String> list, String delimiter) {
        StringBuilder returnStr = new StringBuilder("");
        try {
            if (list != null && list.size() > 0) {
                for (String rec : list) {
                    if (returnStr.length() <= 0) {
                        returnStr = returnStr.append(rec);
                    } else {
                        returnStr = returnStr.append(delimiter).append(rec);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
        return returnStr.toString();
    }

    public ArrayList<String> explodeString(String str, String delimiter) {
        ArrayList<String> list = new ArrayList<String>();
        try {
            if (str != null) {
                String[] arrStr = str.split(delimiter);
                if (arrStr.length > 0) {
                    for (String splitStr : arrStr) {
                        list.add(splitStr);
                    }
                } else {
                    list.add(str);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return list;
    }

    public User checkUser(String sessionId, String email, String password) throws Exception {
        // check if user is exist in our system by username/email/mobile
        User user = authDao.getUserByUsername(email);
        if (user == null || user.getId() == 0) {
            throw new Exception("User not exist.");
        }
        // check if user is active or not
        if (user.getStatus().getId() != Status.ACTIVE) {
            throw new Exception("User not active");
        }

        AuthUser authUser = authDao.getUserCredentialByUsernameAndPassword(user.getId(), password);
        if (authUser == null || authUser.getUserId() == 0) {
            throw new Exception("User And password missmatch.");
        }

        return user;
    }

}
