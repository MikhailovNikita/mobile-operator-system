<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="../../../resources/css/materialize.css">
    <link rel="stylesheet" type="text/css" href="../../../resources/css/bootstrap.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        function disableOption(contractId, optionId, contractBlocked) {
            if (!contractBlocked) {
                popbox(contractId, optionId);
            } else {
                alert('You can not change blocked contract');
            }

            function popbox(contractId, optionId) {
                var x = confirm('Are you sure?');
                if (x === true) {
                    $.ajax({
                        type: 'POST',
                        url: '/admin/disable_option',
                        data: {
                            contractId: contractId,
                            optionId: optionId
                        }
                    })

                    $.ajax({
                        type: 'POST',
                        url: '/admin/show_contract',
                        data: {
                            contractId: contractId
                        },
                        success: function () {
                            location.reload();
                        }
                    });
                }
            }
        }

        function changeBlockStatus(block, contractId) {
            var x = confirm('Are you sure?');

            if (x === true) {
                if (block) {
                    $.ajax({
                        type: 'POST',
                        url: '/admin/block_contract',
                        data: {
                            contractId: contractId,
                        }
                    });
                } else {
                    $.ajax({
                        type: 'POST',
                        url: '/admin/unblock_contract',
                        data: {
                            contractId: contractId,
                        }
                    });
                }

                $.ajax({
                    type: 'POST',
                    url: '/admin/show_contract',
                    data: {
                        contractId: contractId
                    },
                    success: function () {
                        location.reload();
                    }
                });

            }
        }

    </script>
</head>
<body>
<jsp:include page="admin_header.jsp"/>

<div class="container-fluid" style="margin-top: 65px">
    <div class="jumbotron">
        <h3>Contract: ${contract.number}</h3>
        <div>
            <a class="text-muted">Created on: ${contract.conclusionDate}</a><br>
            <a>Status:
                <c:if test="${contract.blockedByAdmin}">
                    <a style="color: red">Blocked by operator</a><br>
                    <button type="submit" class="btn btn-danger"
                            onclick="changeBlockStatus(false, ${contract.contractId})">
                        Unblock
                    </button><br>
                </c:if>
                <c:if test="${contract.blockedByUser && !contract.blockedByAdmin}">
                    <a>Blocked by client</a><br>

                    <button type="submit" class="btn btn-danger"
                            onclick="changeBlockStatus(true, ${contract.contractId})">
                        Block
                    </button><br>
                </c:if>
                <c:if test="${!contract.blockedByAdmin && !contract.blockedByUser}">
                    <a style="color: green">Active</a><br>
                    <button type="submit" class="btn btn-danger"
                            onclick="changeBlockStatus(true, ${contract.contractId})">
                        Block
                    </button><br>
                </c:if>
            </a>
            <a>Tariff: ${contract.tariffName} </a>
            <c:if test="${!contract.blockedByUser && !contract.blockedByUser}">
                <form method="POST" action="/admin/tariffs_for_change">
                    <input type="hidden" value="${contract.contractId}" name="contractId"/>
                    <button type="submit" class="btn btn-primary">Change</button>
                </form>
            </c:if>
            <br>
        </div>

        <div>
            <h5>Enabled options</h5>
            <table class="table table-hover" style="width: 50%">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Cost</th>
                    <th>Access cost</th>

                    <th style="width: 10%">Action</th>
                </tr>
                </thead>
                <c:forEach items="${contract.options}" var="option">
                    <tr>
                        <td>${option.name}</td>
                        <td>${option.cost}</td>
                        <td>${option.accessCost}</td>
                        <td>
                            <button class="btn btn-danger" name="disableOption"
                                    onclick="disableOption(${contract.contractId}, ${option.id},
                                        ${contract.blockedByUser || contract.blockedByAdmin})">
                                Disable
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <div>
            <form method="POST" action="/admin/options_for_activation">
                <input type="hidden" name="contractId" value="${contract.contractId}"/>
                <button class="btn btn-success" type="submit">Activate new option</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
