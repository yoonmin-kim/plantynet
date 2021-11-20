<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@page import="hello.com.plantynet.domain.ItemType" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <title>상품수정</title>
</head>
<body>
<div class="container" style="max-width: 600px">

    <div class="py-5 text-center">
        <h2>상품 수정 폼</h2>
    </div>


    <form:form modelAttribute="item" action="" method="post">
        <div>
            <p class="field-error"><form:errors/></p>
        </div>

        <div>
            <label for="itemName">상품명(필수값)</label>
            <form:input type="text" id="itemName" name="itemName" class="form-control"
                        placeholder="이름을 입력하세요" path="itemName" cssErrorClass="form-control field-error"/>
            <div class="field-error"><form:errors path="itemName"/></div>
        </div>

        <div>
            <label for="price">가격(필수값, 최소500원)</label>
            <form:input type="text" id="price" name="price" class="form-control"
                        placeholder="가격을 입력하세요" path="price" cssErrorClass="form-control field-error"/>
            <div class="field-error"><form:errors path="price"/></div>
        </div>

        <div>
            <label for="quantity">수량(필수값, 1000개제한)</label>
            <form:input type="text" id="quantity" name="quantity" class="form-control"
                        placeholder="수량을 입력하세요" path="quantity" cssErrorClass="form-control field-error"/>
            <div class="field-error"><form:errors path="quantity"/></div>
        </div>

        <!-- SELECT -->
        <div>
            <div>배송 방식</div>
            <form:select id="deliveryCode" name="deliveryCode" class="form-select" path="deliveryCode">
                <form:option value="">==배송 방식 선택==</form:option>
                <c:forEach var="deliveryCode" items="${deliveryCodes}">
                    <form:option value="${deliveryCode.code}">${deliveryCode.displayName}</form:option>
                </c:forEach>
            </form:select>
        </div>

        <hr class="my-4">

        <!-- single checkbox -->
        <div>판매 여부</div>
        <div>
            <div class="form-check">
                <form:checkbox id="open" name="open" class="form-check-input" path="open"/>
                <label for="open" class="form-check-label">판매 오픈</label>
            </div>
        </div>

        <!-- multi checkbox -->
        <div>
            <div>등록 지역</div>
            <c:forEach var="region" items="${regions}" varStatus="status">
                <div class="form-check form-check-inline">
                    <form:checkbox id="regions${status.count}" name="regions"
                                   value="${region.key}" class="form-check-input" path="regions"/>
                    <label for="regions${status.count}" class="form-check-label">${region.value}</label>
                </div>
            </c:forEach>
        </div>
        <!-- radio button -->
        <div>
            <div>상품 종류</div>
            <c:forEach var="type" items="${ItemType.values()}" varStatus="status">
                <div class="form-check form-check-inline">
                    <form:radiobutton id="itemType${status.count}" name="itemType" value="${type.name()}"
                                      class="form-check-input" path="ItemType"/>
                    <label for="itemType${status.count}" class="form-check-label">${type.description}</label>
                </div>
            </c:forEach>
        </div>

        <div class="row">
            <div class="col">
                <button class="w-100 btn btn-primary btn-lg" type="submit">상품 등록</button>
            </div>
            <div class="col">
                <button class="w-100 btn btn-secondary btn-lg"
                        onclick="location.href='/jsp/editList'" type="button">취소
                </button>
            </div>
        </div>
    </form:form>
</div> <!-- /container -->
</body>
</html>
