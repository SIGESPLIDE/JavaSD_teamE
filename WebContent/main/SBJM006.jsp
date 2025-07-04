<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- 作成者　潟辺　陸 -->
<c:import url="/BASE001.jsp">

    <%--
        headパラメータ：ページの主要な見出しとして「科目情報削除」を渡します。
    --%>
    <c:param name="head">
        <h2>科目情報削除</h2>
    </c:param>

    <%--
        bodyパラメータ：画面の本体となるコンテンツを渡します。
        ここが改善のメイン部分です。
    --%>
    <c:param name="body">
        <section>
            <%--
                [改善点1] ユーザーへの確認メッセージを表示します。
                SubjectDeleteControllerから渡された 'subject' オブジェクトの情報を使います。
            --%>
            <c:if test="${not empty subject}">
                <p class="mt-4">
                    「<c:out value="${subject.name}" />(<c:out value="${subject.cd}" />)」を削除してもよろしいですか
                </p>

                <%--
                    削除を実行するサーブレット (subjectDeleteExecute) に情報を送るフォームです。
                --%>
                <form action="subjectDeleteExecute" method="post" class="mt-3">
                    <%--
                        [改善点2] サーブレットに渡すパラメータを修正します。
                        ・nameを "id" から "cd" に変更。
                        ・valueをサーブレットから受け取った ${subject.cd} に変更。
                    --%>
                    <input type="hidden" name="cd" value="<c:out value="${subject.cd}" />">

                    <button type="submit" class="btn btn-danger">削除</button>
                </form>
            </c:if>

            <%-- もしsubjectオブジェクトが渡されなかった場合のエラー表示 --%>
            <c:if test="${empty subject}">
                <p class="text-danger mt-4">削除対象の科目が指定されていません。</p>
            </c:if>

            <%--
                [改善点3] 「戻る」リンクの行き先を修正します。
                JSPファイルに直接ではなく、科目一覧を表示する機能（サーブレットやAction）にリンクします。
            --%>
            <div class="mt-3">
                <a href="SubjectList.action">戻る</a>
            </div>
        </section>
    </c:param>

</c:import>