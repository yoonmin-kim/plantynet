<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <title>플랜티넷 미니프로젝트</title>
<body>

<headr>
    <div class="px-3 py-2 border-bottom mb-3">
        <div class="container d-flex flex-wrap justify-content-center">
            <div class="col-12 col-lg-auto mb-2 mb-lg-0 me-lg-auto"></div>
            <div class="text-end">
                <a class="btn btn-primary" href="/logout">로그아웃</a>
            </div>
        </div>
    </div>
</headr>

<div class="container" style="max-width: 600px">

    <div class="py-5 text-center">
        <h2>상품 서비스</h2>
    </div>

    <div class="row g-2">
        <div class="col-6">
            <div class="p-3 bg-light">
                <button type="button" class="btn btn-primary w-100 btn-lg"
                        onclick="location.href='/jsp/item'">등록
                </button>
            </div>
        </div>
        <div class="col-6">
            <div class="p-3 bg-light">
                <button type="button" class="btn btn-primary w-100 btn-lg"
                        onclick="location.href='/jsp/list'">목록
                </button>
            </div>
        </div>
        <div class="col-6">
            <div class="p-3 bg-light">
                <button type="button" class="btn btn-primary w-100 btn-lg"
                        onclick="location.href='/jsp/editList'">수정
                </button>
            </div>
        </div>
        <div class="col-6">
            <div class="p-3 bg-light">
                <button type="button" class="btn btn-primary w-100 btn-lg"
                        onclick="location.href='/jsp/deleteList'">삭제
                </button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    <c:if test="${accessDenied}">
        alert('해당 메뉴에 대한 권한이 없습니다.');
    </c:if>
</script>
</body>
</html>

