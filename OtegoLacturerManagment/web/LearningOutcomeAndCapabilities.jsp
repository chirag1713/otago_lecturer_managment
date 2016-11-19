<jsp:include page="header.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">$(document).ready(showHideTab);</script>

<!-- Being Page Title -->
<!--<div class="container">
    <div class="page-title clearfix">
        <div class="row">
            <div class="col-md-12">
                <h6><a href="index.php">Home</a></h6>
                <h6><span class="page-active">Courses</span></h6>
            </div>
        </div>
    </div>
</div>-->


<div class="container">
    <div class="row">

        <!-- Here begin Main Content -->
        <div class="col-md-12">

            <div class="row">
                <div class="col-md-12">
                    <div class="widget-main">
                        <div class="widget-inner" style="padding: 0px">
                            <div class="panel with-nav-tabs panel-default footer-widget-title">
                                <div class="panel-heading" style="font-size: 16px;">
                                    <ul class="nav nav-tabs">
                                        <li class="active" id="items"><a href="#tab1default" data-toggle="tab">ITEMS</a></li>
                                        <li id="capabilities"><a href="#tab2default" data-toggle="tab">LERNER CAPABILITIES </a></li>
<!--                                        <li><a href="#tab3default" data-toggle="tab">Default 3</a></li>-->
                                        <!--                                        <li class="dropdown">
                                                                                    <a href="#" data-toggle="dropdown">Dropdown <span class="caret"></span></a>
                                                                                    <ul class="dropdown-menu" role="menu">
                                                                                        <li><a href="#tab4default" data-toggle="tab">Default 4</a></li>
                                                                                        <li><a href="#tab5default" data-toggle="tab">Default 5</a></li>
                                                                                    </ul>
                                                                                </li>-->
                                    </ul>
                                </div>
                                <div class="panel-body footer-widget-title" style="padding: 0px">
                                    <div>
                                        <div class="tab-pane fade in active" id="tab1" >  
                                             <ul class="list-group">
                                           <c:forEach items="${learningOutcomeList}" var="outcome">
                    <a data-toggle="modal" data-target="#myModal" class="list-group-item" style="border:1px solid #f5eeee;">${outcome.content}</a>
                </c:forEach>
                </ul>
                                       </div>
                                    <div class="tab-pane fade in active" id="tab2" >  
<%--                                              <c:forEach items="${learningOutcomeList}" var="outcome">
 --%>                                              <ul class="list-group">
                    <a data-toggle="modal" data-target="#myModal" href="#" class="list-group-item">Cras justortgertertgre odio</a>
                    <a href="http://www.jquery2dotnet.com/2014/01/two-way-scrolling-in-single-div.html" class="list-group-item">Dapibus ac twetgetgfacilisis in</a>
                    <a href="#" class="list-group-item">Morbi leogfdg risus</a>
                    <a href="#" class="list-group-item">Porta ac gedgconsectetur ac</a>
                    <a href="#" class="list-group-item">Vestibulumgdhg at eros</a>
                </ul>
<%--                                             </c:forEach>
 --%>                                        </div>
                                        <!--                                        <div class="tab-pane fade" id="tab3default">Default 3</div>
                                                                                <div class="tab-pane fade" id="tab4default">Default 4</div>
                                                                                <div class="tab-pane fade" id="tab5default">Default 5</div>-->
                                    </div>
                                </div>
                            </div>

                        </div> <!-- /.widget-inner -->
                    </div> <!-- /.widget-main -->
                </div> <!-- /.col-md-12 -->
            </div> <!-- /.row -->
        </div> <!-- /.col-md-8 -->
    </div> <!-- /.row -->
</div> <!-- /.container -->


<div id="myModal" class="modal fadeOut">

    <div class="modal-dialog" style="left: 0;">

        <div class="modal-content">

            <div class="modal-header" style="background:#004F9F;">

                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" style="color: #fff;">&times;</button>

                <h4 class="modal-title" style="color: #fff;">Web 2 - Programming Course</h4>

            </div>
            <form class="form-check-label" style="padding-left: 20px;padding-right: 20px; padding-top: 10px">

                <div class="form-group">
<!--                    <label for="exampleSelect1">Example select</label>-->
                    <select class="form-control" id="exampleSelect1">
                        <option >Select Option</option>
                        <option>START OF THE COURSE, 2 WEEKS</option>
                        <option>MID-COURSE, 4 WEEKS </option>
                        <option>END OF COURSE, 2 WEEKS</option>
                    </select>
                </div>
                <!--                <div class="form-group">
                                    <label for="exampleInputEmail1">Email address</label>
                                    <input type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Enter email">
                                    <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
                                </div>-->

                <div class="form-group">
                    <!--                    <label for="exampleTextarea">Example textarea</label>-->
                    <textarea class="form-control" id="exampleTextarea" rows="3" placeholder='&#xf075; Add Comment'></textarea>
                </div>
                <!--                <button type="submit" class="btn btn-primary">Submit</button>-->
                <div class="modal-footer">

                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>

                    <button type="button" class="btn btn-primary">Save changes</button>

                </div>
            </form>
        </div>

    </div>

</div>

<jsp:include page="footer.jsp"></jsp:include>
