<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Trang chi tiết</title>
<link
    href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
    rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/styles.css"
    rel="stylesheet" type="text/css" />
<link rel="stylesheet"
    href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" />
</head>

<body>
    <%@include file="layout/header.jsp"%>

    <div class="container user-details">
        <h2>User Details</h2>
        <form action="${pageContext.request.contextPath}/updateUser" method="post" style="display:inline;">
            <div class="row">
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">User ID</h5>
                            <p class="card-text">${user.userId}</p>
                            <input type="hidden" name="userId" value="${user.userId}">
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Username</h5>
                            <p class="card-text">${user.username}</p>
                            <input type="hidden" name="username" value="${user.username}">
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Email</h5>
                            <input type="email" class="form-control input-details" name="email" value="${user.email}">
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Password</h5>
                            <input type="password" class="form-control input-details" name="password" value="${user.password}">
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Public Key</h5>
                            <input type="text" id="publicKey" class="form-control input-details"
                            style="background: white;" name="publicKey" value="${user.publicKey}" readonly="readonly">
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Two Factor Enabled</h5>
                            <input type="checkbox" class="form-check-input" style="margin-left: 0; margin-top: 12px;" name="twoFactorEnabled" value="true" ${user.twoFactorEnabled ? "checked" : ""}>
                            <input type="hidden" name="_twoFactorEnabled" value="on">
                            <label style="padding: 6px 12px 6px 0;margin: 1px 1px 1px 20px;" class="form-check-label">${user.twoFactorEnabled ? "Enabled" : "Disabled"}</label>
                        </div>
                    </div>
                </div>
            </div>
            <button type="submit" class="btn btn-outline-primary">Cập nhật</button>
        </form>
        <form action="${pageContext.request.contextPath}/logout" method="get" style="display:inline;">
            <button type="submit" class="btn btn-outline-danger">Đăng xuất</button>
        </form>
    </div>

    <%@include file="layout/footer.jsp"%>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://unpkg.com/@solana/web3.js@latest/lib/index.iife.js"></script>
</body>

</html>
