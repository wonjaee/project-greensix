/**
 * 
 */
$(function() {
	var li = $("#gnb > .wrap > ul > li");
	li.mouseenter(function() {
	});

	li.mouseleave(function() {
	});

});

function categoryMenu(tag){
	var parentNo=$(tag).attr("value");
	var no=$(tag).index()
	var len= $(".menu-list > div").length
	console.log(len)
	$.ajax({
			url: `/category/${parentNo}`,
			success: function(result) {
				$(".sub-list").html(result)
				var menuH= -(50*len) +(50*no) + 'px';
				$(".sub-list").css("left","179px")
				$(".sub-list").css("top",menuH)
			}
		})
}

function showSubMenu() {

	var parentNo = $("#gnb > .wrap > ul > li:first-child").val();

	if (parentNo == 0) {
		$.ajax({
			url: `/category/${parentNo}`,
			success: function(result) {
				$(".menu-list").html(result)
			}
		})

	}

	$(".menu-list").show()
	$(".sub-list").show()
		
	
}

function hideSubMenu() {
	$("#gnb > .wrap > ul > li:nth-of-type(1) > div").hide()
	//$("#gnb > .wrap > ul > li:nth-of-type(" + index + ") > div.sub-list > *").remove()
}