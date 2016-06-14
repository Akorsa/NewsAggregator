<%@ include file="../layout/taglib.jsp" %>

<!-- Button trigger modal -->
<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
    New blog
</button>


<form:form commandName="blog" cssClass="form-horizontal blogForm">
    <!-- Modal -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">New blog</h4>
                </div>
                <div class="modal-body">

                    <div class="form-group name">
                        <label for="name" class="col-sm-2 control-label">Name:</label>

                        <div class="col-sm-10">
                            <form:input path="name" cssClass="form-control"/>
                            <form:errors path="name"/>
                        </div>
                    </div>
                    <div class="form-group radio-group">
                        <div class="radio col-sm-offset-2">
                            <label><form:radiobutton path="isRss" id="r1" value="yes"/>RSS</label>
                        </div>
                        <div class="radio col-sm-offset-2">
                            <label><form:radiobutton path="isRss" value="no"/>Parse blog</label>
                        </div>
                    </div>
                    <div class="form-group urlRss">
                        <label for="urlRss" class="col-sm-2 control-label">URL:</label>
                        <div class="col-sm-10">
                            <form:input path="urlRss" cssClass="form-control"/>
                            <form:errors path="urlRss"/>
                        </div>
                    </div>
                    <div class="form-group list">
                        <label for="sel1" class="col-sm-2 control-label">Choose website:</label>
                        <div class="col-sm-10">
                            <form:select path="url" cssClass="form-control" id="sel1">
                                <c:forEach items="${sites}" var="site">
                                    <form:option value="${site}">
                                        ${site}
                                    </form:option>
                                </c:forEach>
                            </form:select>
                        </div>
                    </div>


                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <input type="submit" class="btn btn-primary" value="Save"/>
                </div>
            </div>
        </div>
    </div>
</form:form>

<br/><br/>

<script type="text/javascript">
    $(document).ready(function () {
        $('.nav-tabs a:first').tab('show'); // Select first tab
        $("#r1").prop("checked", true);
        $(".list").hide();
        $('input[type="radio"]').click(function () {
            if ($(this).attr("value") == "yes") {
                $(".list").hide();
                $(".urlRss").show();
            }
            if ($(this).attr("value") == "no") {
                $(".list").show();
                $(".urlRss").hide();
            }
        });
        $(".triggerRemove").click(function (e) {
            e.preventDefault();
            $("#modalRemove .removeBtn").attr("href", $(this).attr("href"));
            $("#modalRemove").modal();
        });
        $(".blogForm").validate(
                {
                    rules: {
                        name: {
                            required: true,
                            minlength: 1
                        },
                        urlRss: {
                            required: true,
                            urlRss: true
                        },
                    },
                    highlight: function (element) {
                        $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
                    },
                    unhighlight: function (element) {
                        $(element).closest('.form-group').removeClass('has-error').addClass('has-success');
                    }
                }
        );
    });
</script>

<!-- Nav tabs -->
<ul class="nav nav-tabs">
    <c:forEach items="${user.blogs}" var="blog">
        <li><a href="#blog_${blog.id}" data-toggle="tab"><c:out value="${blog.name}"/></a></li>
    </c:forEach>
</ul>

<!-- Tab panes -->
<div class="tab-content">
    <c:forEach items="${user.blogs}" var="blog">
        <div class="tab-pane" id="blog_${blog.id}">
            <h1><c:out value="${blog.name}"/></h1>

            <p>

                <a href="<spring:url value="/blog/remove/${blog.id}.html" />" class="btn btn-danger triggerRemove">remove
                    blog</a>

                <c:out value="${blog.urlRss}"/> <c:out value="${blog.url}"/></p>

            <table class="table table-bordered table-hover table-striped">
                <thead>
                <tr>
                    <th>date</th>
                    <th>item</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${blog.items}" var="item">
                    <tr>
                        <td><c:out value="${item.publishedDate}"/></td>
                        <td>
                            <strong>
                                <a href="<c:out value="${item.link}"/>" target="_blank">
                                    <c:out value="${item.title}"></c:out>
                                </a>
                            </strong>
                            <br/>
                                ${item.description}
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:forEach>
</div>


<!-- Modal -->
<div class="modal fade" id="modalRemove" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Remove blog</h4>
            </div>
            <div class="modal-body">
                Really remove?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <a href="" class="btn btn-danger removeBtn">Remove</a>
            </div>
        </div>
    </div>
</div>