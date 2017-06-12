package com.yd.gcj.system.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yd.gcj.entity.vo.YdMangerBankTRVo;
import com.yd.gcj.system.service.YdMangerServiceSystemBankroll;
import com.yd.gcj.util.MyFileUtils;

/**
 * description(资金管理)
 * 
 * @author Administrator
 * @param <HttpServletRequest>
 */
@Controller
@RequestMapping("/system")
public class YdMangerControllerSystemBankroll {

	@Autowired
	private YdMangerServiceSystemBankroll ydMangerServiceSystemBankroll;

	/**
	 * description(查询所有用户的充值记录)
	 * 
	 * @param
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryUserRechargeRecord")
	public String queryUserRechargeRecord(Integer p, String user_name, String user_phone, Model model) {
		// 当前页
		if (p == null) {
			p = 1;
			PageHelper.startPage(p, 8);
		} else {
			PageHelper.startPage(p, 8);
		}
		List<YdMangerBankTRVo> bankTRVoList = ydMangerServiceSystemBankroll.queryUserRechargeRecord(user_name,
				user_phone);
		PageInfo<YdMangerBankTRVo> pageInfo = new PageInfo<YdMangerBankTRVo>(bankTRVoList);

		// 总页数
		Integer totalPage = null;
		Integer total = (int) pageInfo.getTotal();
		if (total % 5 != 0) {
			totalPage = total / 8 + 1;
		} else {
			totalPage = total / 8;
		}
		model.addAttribute("p", p);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("bankTRVoList", bankTRVoList);
		model.addAttribute("user_name", user_name);
		model.addAttribute("user_phone", user_phone);
		return "system/zjgl/bankroll";
	}

	@RequestMapping("/excelExport")
	@ResponseBody
	public Object excelExport(String user_name, String user_phone, HttpServletResponse response,
			HttpServletRequest request) throws IOException {
		List<YdMangerBankTRVo> list = ydMangerServiceSystemBankroll.queryUserRechargeRecord(user_name, user_phone);
		HSSFWorkbook workbook = new HSSFWorkbook();
		// HSSFSheet sheet = workbook.createSheet("效益指标");
		HSSFSheet sheet = workbook.createSheet("分区信息一");
		// 在索引0的位置创建行（最顶端的行）
		HSSFRow row = sheet.createRow(0);
		// 在索引0的位置创建单元格（左上端）
		row.createCell(0).setCellValue("用户ID");
		row.createCell(1).setCellValue("用户姓名");
		row.createCell(2).setCellValue("用户昵称");
		row.createCell(3).setCellValue("用户手机号");
		row.createCell(4).setCellValue("用户邮箱");
		row.createCell(5).setCellValue("充值金额");
		row.createCell(6).setCellValue("账户总金额");
		row.createCell(7).setCellValue("银行名称");
		row.createCell(8).setCellValue("充值账号");
		row.createCell(9).setCellValue("类型");
		row.createCell(10).setCellValue("充值时间");
		if (list != null && list.size() != 0) {
			// sheet.getLastRowNum() 获取 当前 sheet 分区最后一行行号
			for (YdMangerBankTRVo s : list) {
				// 1 2 3 4
				int lastRowNum = sheet.getLastRowNum();
				HSSFRow newRow = sheet.createRow(lastRowNum + 1);
				newRow.createCell(0).setCellValue(s.getBtr_uid());
				newRow.createCell(1).setCellValue(s.getUser_name());
				newRow.createCell(2).setCellValue(s.getNickname());
				newRow.createCell(3).setCellValue(s.getUser_phone());
				newRow.createCell(4).setCellValue(s.getUser_email());
				newRow.createCell(5).setCellValue(s.getBtr_price());
				newRow.createCell(6).setCellValue(s.getUser_cprice());
				newRow.createCell(7).setCellValue(s.getBank_name());
				newRow.createCell(8).setCellValue(s.getBtr_account());
				newRow.createCell(9).setCellValue(s.getBtrType());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				String formatDate = sdf.format(s.getBtr_create_time());
				newRow.createCell(10).setCellValue(formatDate);
			}
			// 内存中 workbook 工作簿数据 生成
		}
		// 2: 工作簿 内存数据 输出 写 response.getOutputStream 定义 两个头 一个 附件头attachemt
		// 一个mime 类型头 ServletContext 对象
		String filename = "个人充值记录.xls";// 不同浏览器附件附件名称乱码 火狐 base64 ie/chrome url

		String mime = request.getSession().getServletContext().getMimeType(filename);

		response.setContentType(mime);

		response.setHeader("Content-Disposition",
				"attachment;filename=" + MyFileUtils.encodeDownloadFilename(filename, request.getHeader("user-agent")));
		// 输出流
		try {
			workbook.write(response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

}