<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <title>상품목록</title>
</head>
<body>
<div class="container" style="max-width: 600px">

    <div class="py-5 text-center">
        <h2>상품목록</h2>
    </div>

    <div class="row">
        <div class="col">
            <button class="btn btn-primary float-end"
                    onclick="location.href='/jsp'"
                    type="button">홈으로
            </button>
        </div>
    </div>

    <hr class="my-4">

    <table class="table">
        <thead>
        <tr>
            <th scope="col">상품번호</th>
            <th scope="col">상품명</th>
            <th scope="col">가격</th>
            <th scope="col">수량</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${items}">
            <tr>
                <th scope="row">${item.id}</th>
                <td>${item.itemName}</td>
                <td>${item.price}</td>
                <td>${item.quantity}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>
</body>
</html>
