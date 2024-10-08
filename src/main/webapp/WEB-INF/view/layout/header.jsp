<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<header>
    <div class="container">
        <div class="row align-items-center">
            <div class="col-2">
                <a class="nav-link" href="/" style="width: 75%;">
                	<img src="${pageContext.request.contextPath}/img/Logo.png" alt="Logo" class="img-fluid" />
                </a>
            </div>
            <div class="col-10">
                <nav class="navbar navbar-expand-lg navbar-light justify-content-center">
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                        aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse justify-content-center" id="navbarNav">
                        <ul class="navbar-nav">
                            <li class="nav-item">
                                <a class="nav-link" href="/"><i class="bi bi-house"></i> Trang Chủ</a>
                            </li>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <i class="bi bi-ui-checks-grid"></i> Danh Mục
                                </a>
                                <div class="dropdown-menu menu-danhmuc" aria-labelledby="navbarDropdown">
                                    <a class="dropdown-item" href="/products?gameName=TFT">Lãng mạn</a>
                                    <a class="dropdown-item" href="/products?gameName=LOL">Kinh dị</a>
                                    <a class="dropdown-item" href="/products?gameName=Valorant">Hành động</a>
                                </div>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#"><i class="bi bi-people"></i> Về Chúng Tôi</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/contact"><i class="bi bi-telephone-inbound"></i> Liên Hệ</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/cart"><i class="bi bi-bookmark-heart"></i> Yêu thích</a>
                            </li>

                            <c:choose>
                                <c:when test="${not empty sessionScope.loggedInUser}">
                                    <li class="nav-item nav-dangnhap">
                                        <a class="nav-link" id="NavDangNhap" href="/user/${sessionScope.loggedInUser.userId}">
                                            <i class="bi bi-person"></i> ${sessionScope.loggedInUser.username}
                                        </a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="nav-item nav-dangnhap">
                                        <a class="nav-link" id="NavDangNhap" href="/login"><i class="bi bi-person"></i> Đăng Nhập</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </ul>
                    </div>
                </nav>
            </div>
        </div>
    </div>
</header>
