$(function(){
	
	// 收货地址选择
	$(".settlement-page .buyer").on("click"," ul li i",function(){
		$(this).parents("li").addClass("active").siblings().removeClass("active");
	});
	
	// 送货方式
	$(".settlement-page .inventory .left ul li").click(function(){
		$(this).addClass("active").siblings().removeClass("active");
	});
	
	
	// 新增收货地址
	$(".settlement-page .section .title a.add-address").click(function(){
		$(".add-consignee").fadeIn(300);
		$(".add-consignee .add-consignee-c").css({"-webkit-transform":"scale(1)","transform":"scale(1)"});
	});
	
	$(".add-consignee .add-consignee-c .title span").click(function(){
		$(".add-consignee .add-consignee-c").css({"-webkit-transform":"scale(0)","transform":"scale(0)"});
		$(".add-consignee").fadeOut(300);
	});
	
	// 支付方式选择
	$(".cashier-page .payment-type ul li").click(function(){
		$(this).addClass("active").siblings().removeClass("active");
	});
	
	// 新增地址确认
	$(".add-consignee-c .content li.personal-sub input").click(function(){
		var html="<li><i></i>";
		html += "<span>" + $("input.consignee").val() + "</span>";
		html += "<span>" + $("select.province").val() + $("select.city").val() + $("select.area").val() + "&nbsp;" + $("input.detail-address").val() + "</span>";
		html += "<span>" + $("input.moblie").val() + "</span>";
		html += "</li>";
		$(".settlement-page .section .buyer ul").prepend(html);
		$(".add-consignee .add-consignee-c").css({"-webkit-transform":"scale(0)","transform":"scale(0)"});
		$(".add-consignee").fadeOut(300);
		$(".settlement-page .section .buyer ul li").eq(0).addClass("active").siblings().removeClass("active");
	});
	
	
	
	// 新增发票单位
	$(".settlement-page .invoice a").click(function(){
		$(".add-invoice").fadeIn(300);
		$(".add-invoice .add-invoice-c").css({"-webkit-transform":"scale(1)","transform":"scale(1)"});
	});
	
	$(".add-invoice .add-invoice-c .title span").click(function(){
		$(".add-invoice .add-invoice-c").css({"-webkit-transform":"scale(0)","transform":"scale(0)"});
		$(".add-invoice").fadeOut(300);
	});
	
	
	$(".add-invoice .add-invoice-c .content ul li > span").click(function(){
		var ddLength = $(".add-invoice-c .content ul li:first-child dl dd").length;
		if( ddLength < 5 ){
			$(".add-invoice-c .content ul li:first-child dl").append('<dd><input type="text" value="" placeholder="新增单位发票抬头" /><span class="save">保存</span></dd>');
		}
	});
	
	//保存
	$(".add-invoice-c .content ul li:first-child dl").on("click","dd span.save",function(){
		var val = $(this).parents("dd").find("input").val();
		if( val != ""){
			$(".add-invoice-c .content ul li:first-child dl dd").removeClass("active");
			$(this).parents("dd").addClass("active").html('<input type="text" value="'+val+'" disabled="disabled" /><span class="del">删除</span><span class="edit">编辑</span>');
		}
	});
	
	//删除
	$(".add-invoice-c .content ul li:first-child dl").on("click","dd span.del",function(){
		$(this).parents("dl").find("dd").eq(0).addClass("active");
		$(this).parents("dd").remove();
	});
	
	//编辑
	$(".add-invoice-c .content ul li:first-child dl").on("click","dd span.edit",function(){
		var val = $(this).parents("dd").find("input").val();
		$(".add-invoice-c .content ul li:first-child dl dd").removeClass("active");
		$(this).parents("dd").html('<input type="text" value="'+val+'" /><span class="save">保存</span>');
	});
	
	$(".add-invoice-c .content ul li dl").on("click","dd",function(){
		$(this).addClass("active").siblings().removeClass("active");
	});
	
	$(".add-invoice-c .content ul li .submit").click(function(){
		var fir_index = $(".add-invoice-c .content ul li:first-child dl dd.active").index();
		var sec_index = $(".add-invoice-c .content ul li:first-child + li dl dd.active").index();
		var val = $(".add-invoice-c .content ul li:first-child dl dd.active input").val();
		if( sec_index == 0 ){
			$(".bond-page .order-info dl dd").eq(4).find("span").html("不开发票");
		}else{
			if( fir_index == 0 ){
				$(".settlement-page .invoice .text").html("普通发票（纸质）&nbsp;&nbsp;个人&nbsp;&nbsp;明细");
			}else{
				$(".settlement-page .invoice .text").html("普通发票（纸质）&nbsp;&nbsp;" + val + "&nbsp;&nbsp;明细");
			}
		}
		$(".add-invoice .add-invoice-c").css({"-webkit-transform":"scale(0)","transform":"scale(0)"});
		$(".add-invoice").fadeOut(300);
	});
	
});
