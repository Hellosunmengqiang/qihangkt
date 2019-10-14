$(function() {
	changeBgImg();
	$(window).on("resize",changeBgImg);  //resize:监听视口大小，大小被改变的时刻就会触发后面指定的函数
});

function changeBgImg(){
	var winWidth = $(window).width();
	$("#carousel-qhkt>.carousel-inner>.item").each(function(index,el){
		var div = $(el);
		var bgimg = div.data((winWidth <= 768)?"xs-img":"lg-img");
		if(winWidth <= 768){
			div.html('<img src="'+bgimg+'" alt="carousel">');
		}else{
			div.html("");
			div.css('background',"url('"+bgimg+"') no-repeat");
		}
	});
}

