function clickedCancel(){
	location.href="/common/faq/faqBoard";
}

function submitCheck(){
	var title = $('#title').val();
    var content = $('#content').val();
	
	if (title == "" || title == null) {
		alert("제목을 입력해주세요");
        return false;
    }
    if (content == "" || content == null) {
	   alert("내용을 입력해주세요");
	   return false;
    }
	
	return true;
}