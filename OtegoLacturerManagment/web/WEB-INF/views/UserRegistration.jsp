<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/style.css" />
 <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css" />
  <script type="text/javascript">var baseUrl = '<%=request.getContextPath()%>'</script>
     <script src="<%=request.getContextPath()%>/js/jquery-1.11.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/js/main.js"></script>    
    <link rel="stylesheet" href="../../bootstrap/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="../../dist/css/AdminLTE.min.css">
  <!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
  <link rel="stylesheet" href="../../dist/css/skins/_all-skins.min.css">
  <script type="text/javascript">
            $(document).ready(initAddUpdate);
            </script>
</head>
<body>
 <div class="main">
      <div class="one">
        <div class="register">
          <h3>Create your account</h3>
         
          <form class="form-horizontal" id="reg-form">
              <div class="box-body">
               <div class="form-group">
                  <label class="col-sm-2 control-label" for="inputEmail3">UserName</label>

                  <div class="col-sm-10">
                    <input type="text" placeholder="userName" id="userName" name="userName"  class="form-control">
                  </div>
                </div>
                
                <div class="form-group">
                  <label class="col-sm-2 control-label" for="inputEmail3">Email</label>

                  <div class="col-sm-10">
                    <input type="text" placeholder="email" id="emailId"  name="emailId" class="form-control">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label" for="inputPassword3">Password</label>

                  <div class="col-sm-10">
                    <input type="password" placeholder="password" id="password" name="password" class="form-control">
                  </div>
                </div>
                 <div class="form-group">
                  <label class="col-sm-2 control-label" for="inputEmail3">Mobile</label>

                  <div class="col-sm-10">
                    <input type="text" placeholder="mobile" id="mobile"  name="mobile" class="form-control">
                  </div>
                </div>
                <div class="form-group">
                  <div class="col-sm-offset-2 col-sm-10">
                    <div class="checkbox">
                      <label>
                        <input type="checkbox"> Remember me
                      </label>
                    </div>
                  </div>
                </div>
              </div>
              <!-- /.box-body -->
              <div class="box-footer">
                <button class="btn btn-default" type="submit" >Cancel</button>
                <button class="btn btn-info pull-right" type="button" id="create-account" >Sign in</button>
              </div>
              <!-- /.box-footer -->
            </form>
          <div class="sep">
            <span class="or">OR</span>
          </div>
          <div class="connect">
            <div class="social-buttons facebook">
              <a href="#">
                <span>Facebook</span>
              </a>
            </div>
            <div class="social-buttons twitter">
              <a href="#">
                <span>Twitter</span>
              </a>
            </div>
          </div>
        </div>
      </div>
      
     <!--  <div class="two">
        <div class="register">
          <h3>Create your account</h3>
          <form id="reg-form1">
            <div>
              <label for="name1">Name</label>
              <input type="text" id="name1" spellcheck="false" placeholder="Shridhar Deshmukh"/>
            </div>
            <div>
              <label for="email1">Email</label>
              <input type="text" id="email1" spellcheck="false" placeholder="shridhardeshmukh@xyz.com"/>
            </div>
            <div>
              <label for="username1">Username</label>
              <input type="text" id="username1" spellcheck="false" placeholder="shree33" />
            </div>
            <div>
              <label for="password1">Password</label>
              <input type="password" id="password1" />
            </div>
            <div>
              <label for="password-again1">Password Again</label>
              <input type="password" id="password-again1" />
            </div>
            <div>
              <label></label>
              <input type="submit" value="Create Account" id="create-account1" class="button"/>
            </div>
          </form>
          <div class="sep">
            <span class="or">OR</span>
          </div>
          <div class="connect">
            <div class="social-buttons facebook">
              <a href="#">
                <span>Facebook</span>
              </a>
            </div>
            <div class="social-buttons twitter">
              <a href="#">
                <span>Twitter</span>
              </a>
            </div>
          </div>
        </div>
      </div>
    </div> -->
</body>
</html>