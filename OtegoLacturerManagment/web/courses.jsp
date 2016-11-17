<jsp:include page="header.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
						<div class="widget-inner">
							<div class="container">
								<div class="row">									
									<c:set var="tempPro" value="" />
									<c:forEach items="${courseMappingList}" var="cur" varStatus="index">																														
										<c:if test="${cur.course.program.name != tempPro}">
											<c:if test="${not index.first}">
												</ul>
												</div>
											</c:if>
											<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12 footer-widget">
												<h4>${cur.course.program.name}</h4>
												<ul class="list-links">
										</c:if>											
										<li><a data-toggle="modal" data-target="#myModal">${cur.course.name}</a></li>																				
										<c:set var="tempPro">${cur.course.program.name}</c:set>
									</c:forEach>									
									<!-- 	<div
										class="col-lg-6 col-md-6 col-sm-12 col-xs-12 footer-widget">
										<h4>Graduate Diploma in Applied Management</h4>
										<ul class="list-links">
											<li><a data-toggle="modal" data-target="#myModal">Applied
													Management</a></li>
											<li><a data-toggle="modal" data-target="#myModal">Research
													Methodology</a></li>
											<li><a data-toggle="modal" data-target="#myModal">Entrepreneurship</a></li>

										</ul>
									</div>
									<div
										class="col-lg-6 col-md-6 col-sm-12 col-xs-12 footer-widget">
										<h4>Graduate Diploma in Applied Management</h4>
										<ul class="list-links">
											<li><a data-toggle="modal" data-target="#myModal">Applied
													Management</a></li>
											<li><a data-toggle="modal" data-target="#myModal">Research
													Methodology</a></li>
											<li><a data-toggle="modal" data-target="#myModal">Entrepreneurship</a></li>

										</ul>
									</div> -->
							<!-- 		<div
										class="col-lg-6 col-md-6 col-sm-12 col-xs-12 footer-widget">
										<h4>Graduate Diploma in Applied Management</h4>
										<ul class="list-links">
											<li><a data-toggle="modal" data-target="#myModal">Applied
													Management</a></li>
											<li><a data-toggle="modal" data-target="#myModal">Research
													Methodology</a></li>
											<li><a data-toggle="modal" data-target="#myModal">Entrepreneurship</a></li>

										</ul>
									</div> -->

								</div>
							</div>

						</div>
						<!-- /.widget-inner -->
					</div>
					<!-- /.widget-main -->
				</div>
				<!-- /.col-md-12 -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /.col-md-8 -->
	</div>
	<!-- /.row -->
</div>
<!-- /.container -->


<div id="myModal" class="modal fade">

	<div class="modal-dialog" style="width: auto; left: 0;">

		<div class="modal-content">

			<div class="modal-header" style="background: #004F9F;">

				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true" style="color: #fff;">&times;</button>

				<h4 class="modal-title" style="color: #fff;">Web 2 -
					Programming Course</h4>

			</div>

			<div class="modal-body">
				<div class="widget-inner">
					<div class="course-search">
						<h3>Mapping learning outcomes &amp; key concepts,
							assessments, experiential learning &amp; learner capability</h3>

						<form action="#" method="" id="quick_form"
							class="course-search-form">
							<table class="table table-bordered text-center table-responsive">
								<thead>
									<tr>
										<th class="text-center" style="width: 10%;">ITEAMS</th>
										<th class="text-center" style="width: 22%;">START OF THE
											COURSE, 2 WEEKS</th>
										<th class="text-center" style="width: 46%;">MID-COURSE, 4
											WEEKS</th>
										<th class="text-center" style="width: 22%;">END OF
											COURSE, 2 WEEKS</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td></td>
										<td style="padding: 0;">
											<table class="table table-bordered text-center"
												style="margin: 0;">
												<tbody>
													<tr>
														<td class="text-center" style="width: 50%;">WEEK 1</td>
														<td class="text-center" style="width: 50%;">WEEK 2</td>
													</tr>
												</tbody>
											</table>
										</td>
										<td style="padding: 0;">
											<table class="table table-bordered text-center"
												style="margin: 0;">
												<tbody>
													<tr>
														<td class="text-center" style="width: 25%;">WEEK 3</td>
														<td class="text-center" style="width: 25%;">WEEK 4</td>
														<td class="text-center" style="width: 25%;">WEEK 5</td>
														<td class="text-center" style="width: 25%;">WEEK 6</td>
													</tr>
												</tbody>
											</table>
										</td>
										<td style="padding: 0;">
											<table class="table table-bordered text-center"
												style="margin: 0;">
												<tbody>
													<tr>
														<td class="text-center">WEEK 7</td>
														<td class="text-center">WEEK 8</td>
													</tr>
												</tbody>
											</table>
										</td>
									</tr>
									<tr>
										<td>Experiential Learning</td>
										<td style="padding: 0;">
											<table class="table table-bordered text-center"
												style="margin: 0; height: 225px;">
												<tbody>
													<tr>
														<!--<th class="text-center"><input id="input-tags3" class="demo-default selectized" value="science,biology,chemistry,physics" tabindex="-1" style="display: none;" type="text"></th>-->
														<td style="width: 50%;">
															<p>Class activity 1, stickynotes</p>
															<p>Class activity 2,exploration of ideas</p>
															<p>Class activity 3,class debate</p>
														</td>
														<td style="width: 50%;">
															<p>Class activity,Green initiatives for a major 5
																star hotel in Auckland</p>
															<p>Guest speaker: Green initiatives</p>
														</td>
													</tr>
												</tbody>
											</table>
										</td>
										<td style="padding: 0;">
											<table class="table table-bordered text-center"
												style="margin: 0; height: 225px;">
												<tbody>
													<tr>
														<!--<th class="text-center"><input id="input-tags3" class="demo-default selectized" value="science,biology,chemistry,physics" tabindex="-1" style="display: none;" type="text"></th>-->
														<td style="width: 25%;">
															<p>Video case study, Hotel Babylon</p>
															<p>Class activity,Discussion of issue raised in video</p>
														</td>
														<td style="width: 25%;">
															<p>Class activity,Students select one article on
																current issue.</p>
															<p>Students develop approaches to manage the issue.</p>
														</td>
														<td style="width: 25%;">
															<p>Class activity,Group presentations CRMfailures</p>
															<p>Class activity, Studentsdevelop aCRM program for a
																hotel.</p>
														</td>
														<td style="width: 25%;">
															<p>Guest lecture: Diversity in hotel industry.</p>
															<p>Class activity,Group presentations,problems
																related to diversity inhotels.</p>
														</td>
													</tr>
												</tbody>
											</table>
										</td>
										<td style="padding: 0;">
											<table class="table table-bordered text-center"
												style="margin: 0; height: 225px;">
												<tbody>
													<tr>
														<!--<th class="text-center"><input id="input-tags3" class="demo-default selectized" value="science,biology,chemistry,physics" tabindex="-1" style="display: none;" type="text"></th>-->
														<td style="width: 50%;">
															<p>Class activity,Discuss one major issue affecting
																hotel industry.</p>
															<p>Students’ research on the issue identified.</p>
														</td>
														<td style="width: 50%;">
															<p>Continuation of week 7 activities.</p>
															<p>Students then present the research findings.</p>
														</td>
													</tr>
												</tbody>
											</table>
										</td>
									</tr>
									<tr>
										<td>Personal</td>
										<td style="padding: 0;">
											<table class="table table-bordered text-center"
												style="margin: 0; height: 225px;">
												<tbody>
													<tr>
														<!--<th class="text-center"><input id="input-tags3" class="demo-default selectized" value="science,biology,chemistry,physics" tabindex="-1" style="display: none;" type="text"></th>-->
														<td style="width: 50%;">
															<p>How has globalisation touched each student?</p>
														</td>
														<td style="width: 50%;">
															<p>How relevant were the initiatives developed? How
																effective has student contributed to the group work?</p>
														</td>
													</tr>
												</tbody>
											</table>
										</td>
										<td style="padding: 0;">
											<table class="table table-bordered text-center"
												style="margin: 0; height: 225px;">
												<tbody>
													<tr>
														<!--<th class="text-center"><input id="input-tags3" class="demo-default selectized" value="science,biology,chemistry,physics" tabindex="-1" style="display: none;" type="text"></th>-->
														<td style="width: 25%;">
															<p>Rational for selecting the issue raised in the
																video?</p>
														</td>
														<td style="width: 25%;">
															<p>How has student contributed to the activity? Were
																the approaches developed innovative and effective?</p>
														</td>
														<td style="width: 25%;">
															<p>Student will reflect on their contribution made to
																the CRM program developed.</p>
														</td>
														<td style="width: 25%;">
															<p>Student will reflect on the experience faced by
																them in diversity.</p>
														</td>
													</tr>
												</tbody>
											</table>
										</td>
										<td style="padding: 0;">
											<table class="table table-bordered text-center"
												style="margin: 0; height: 225px;">
												<tbody>
													<tr>
														<!--<th class="text-center"><input id="input-tags3" class="demo-default selectized" value="science,biology,chemistry,physics" tabindex="-1" style="display: none;" type="text"></th>-->
														<td style="width: 50%;">
															<p>How relevant and critical was the issue raised?</p>
														</td>
														<td style="width: 50%;">
															<p>How detail and interesting were the presentations?</p>
														</td>
													</tr>
												</tbody>
											</table>
										</td>
									</tr>
									<tr>
										<td>Personal</td>
										<td style="padding: 0;">
											<table class="table table-bordered text-center"
												style="margin: 0; height: 225px;">
												<tbody>
													<tr>
														<!--<th class="text-center"><input id="input-tags3" class="demo-default selectized" value="science,biology,chemistry,physics" tabindex="-1" style="display: none;" type="text"></th>-->
														<td style="width: 50%;">
															<p>How has globalisation touched each student?</p>
														</td>
														<td style="width: 50%;">
															<p>How relevant were the initiatives developed? How
																effective has student contributed to the group work?</p>
														</td>
													</tr>
												</tbody>
											</table>
										</td>
										<td style="padding: 0;">
											<table class="table table-bordered text-center"
												style="margin: 0; height: 225px;">
												<tbody>
													<tr>
														<!--<th class="text-center"><input id="input-tags3" class="demo-default selectized" value="science,biology,chemistry,physics" tabindex="-1" style="display: none;" type="text"></th>-->
														<td style="width: 25%;">
															<p>Rational for selecting the issue raised in the
																video?</p>
														</td>
														<td style="width: 25%;">
															<p>How has student contributed to the activity? Were
																the approaches developed innovative and effective?</p>
														</td>
														<td style="width: 25%;">
															<p>Student will reflect on their contribution made to
																the CRM program developed.</p>
														</td>
														<td style="width: 25%;">
															<p>Student will reflect on the experience faced by
																them in diversity.</p>
														</td>
													</tr>
												</tbody>
											</table>
										</td>
										<td style="padding: 0;">
											<table class="table table-bordered text-center"
												style="margin: 0; height: 225px;">
												<tbody>
													<tr>
														<!--<th class="text-center"><input id="input-tags3" class="demo-default selectized" value="science,biology,chemistry,physics" tabindex="-1" style="display: none;" type="text"></th>-->
														<td style="width: 50%;">
															<p>How relevant and critical was the issue raised?</p>
														</td>
														<td style="width: 50%;">
															<p>How detail and interesting were the presentations?</p>
														</td>
													</tr>
												</tbody>
											</table>
										</td>
									</tr>
								</tbody>
							</table>
						</form>
					</div>
				</div>
				<!-- /.widget-inner -->
			</div>

			<div class="modal-footer">

				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>

				<button type="button" class="btn btn-primary">Save changes</button>

			</div>

		</div>

	</div>

</div>


<script>
	// 	$(document).ready(function() {
	// 		$('.input-tags3').selectize({
	// 			plugins : [ 'remove_button' ],
	// 			delimiter : ',',
	// 			persist : false,
	// 			create : function(input) {
	// 				return {
	// 					value : input,
	// 					text : input
	// 				}
	// 			}
	// 		});
	// 	});
</script>

<jsp:include page="footer.jsp"></jsp:include>
