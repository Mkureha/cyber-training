package com.example.demo.sys.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.sys.Domain.Paging;
import com.example.demo.sys.Domain.UserVO;
import com.example.demo.sys.Mapper.UserMapper;
import com.example.demo.sys.Service.UserService;

@Controller
public class UserController {
	
	@Autowired
	UserMapper mUserMapper;

	@Autowired
	UserService mUserService;

	@RequestMapping(value="/login", method=RequestMethod.GET)
	private void loginForm() {
		System.out.println("ログイン画面表示");
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	private String login(HttpServletRequest request, RedirectAttributes rttr, @ModelAttribute("UserVO") UserVO user, Model model) throws Exception {
		Date date = new Date();
	    TimeZone timeZoneJP = TimeZone.getTimeZone("Asia/Tokyo");
	    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		HttpSession session = request.getSession();
		UserVO userVO = mUserService.loginService(user);

		//既存session削除
		if ( session.getAttribute("login") != null ){
			session.removeAttribute("login");
		}
		
		if (userVO != null) {
			if (userVO.getLOGIN_FAIL_CNT() >= 5 || userVO.getLOGIN_LOCK_FLG() == 1){
				rttr.addFlashAttribute("msg", 2);

				userVO.incFailCnt();

				mUserService.loginFailCnt(userVO);
				System.out.println("ログイン失敗");

				return "redirect:login";
			} else if (!user.getUSER_PW().equals(userVO.getUSER_PW())) {
				rttr.addFlashAttribute("msg", 1);

				userVO.incFailCnt();

				mUserService.loginFailCnt(userVO);
				System.out.println("ログイン失敗");

				return "redirect:login";
			} else if (user.getUSER_PW().equals(userVO.getUSER_PW())) {
				mUserService.loginReset(user.getUSER_ID());
				session.setAttribute("member", userVO);
				
				fmt.setTimeZone(timeZoneJP); 
				
				System.out.println("ログイン成功！");
				
				session.setAttribute("date", fmt.format(date));
			}
		} else if (userVO == null) {
			rttr.addFlashAttribute("msg", 3);
			System.out.println("ログイン失敗");

			return "redirect:login";
		}

		return "redirect:home";
	}

	@GetMapping("/loginout")
	private String loginout(HttpSession session) {
		session.invalidate();
		
		System.out.println("ログアウト完了");
		
		return "redirect:/login";
	}

	@RequestMapping(value = "/home", method=RequestMethod.GET)
	private String login_OK(HttpSession session) throws Exception {

		System.out.println("ホーム画面表示");

		return "home";
	}
	
	@RequestMapping(value = "/GS/Userlist", method=RequestMethod.GET)
	public String list(HttpSession session, @ModelAttribute("UserVO") UserVO user, @RequestParam(defaultValue = "1") int page, Model model) throws Exception{

		int totalListCnt = mUserService.Usercount();
		Paging pagination = new Paging(totalListCnt, page);
		
		int startIndex = pagination.getStartIndex();
		int pageSize = pagination.getPageSize();
		
		List<UserVO> userVO = mUserService.listpage(startIndex, pageSize);
		
		model.addAttribute("list", userVO);
		model.addAttribute("pagination", pagination);

		System.out.println(pagination.getTotalListCnt() + "件ユーザーリスト出力完了");
		
		return "Userlist";
	}

	@RequestMapping(value = "/GS/insert", method=RequestMethod.GET)
	private String UserInsertForm(HttpSession session) throws Exception {

		System.out.println("新規登録画面表示");
		
		return "Insert";
	}
	
	@RequestMapping(value = "/GS/insert", method=RequestMethod.POST)
	private String UserInsertProc(HttpSession session, RedirectAttributes rttr, @ModelAttribute("UserVO") UserVO user) throws Exception {

		UserVO userInsert = mUserService.loginService(user);
		
		if (userInsert != null) {
			if (user.getUSER_ID().equals(userInsert.getUSER_ID())) {				
				rttr.addFlashAttribute("msg", 1);
				
				System.out.println("登録失敗！");
				
				return "redirect:/GS/insert";
			}
		} else {
			 if(!Pattern.matches("^[a-zA-Z0-9+_.-]*$", user.getUSER_ID())){
				 	rttr.addFlashAttribute("msg", 2);
					
					System.out.println("ユーザーID入力誤り！");
					System.out.println(user.getUSER_ID());
					
					return "redirect:/GS/insert";
			 } else if(!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", user.getMAIL_ADR())){
				 	rttr.addFlashAttribute("msg", 3);
					
					System.out.println("メールアドレス入力誤り！");
					System.out.println(user.getMAIL_ADR());
					
					return "redirect:/GS/insert";
			 }

			System.out.println("登録成功！");

			mUserService.UserInsertService(user);
		}
		return "InsertOK";
	}

	@GetMapping(value = "/GS/insertOK")
	private String InsertOK(HttpSession session) {
		System.out.println("新規登録完了画面");
		
		return "InsertOK";
	}

	@RequestMapping(value = "/GS/Modify/{USER_ID}", method=RequestMethod.GET)
	private String UserDetail(HttpSession session, RedirectAttributes rttr, @ModelAttribute UserVO user, Model model) throws Exception {
		UserVO userModify = mUserService.loginService(user);

		model.addAttribute("member", userModify);

		System.out.println("変更準備！");
		
		return "Modify";
	}

	@RequestMapping(value = "/GS/Modify/{USER_ID}", method=RequestMethod.POST)
	private String UserDetailProc(HttpSession session, HttpServletRequest request, RedirectAttributes rttr, @ModelAttribute("UserVO") UserVO user) throws Exception {
		UserVO userModify = mUserService.loginService(user);

		if (!request.getParameter("USER_PW_CHECK").equals(userModify.getUSER_PW())) {
			rttr.addFlashAttribute("msg", 1);

			System.out.println("変更失敗！");
			
			return "redirect:/GS/Modify/" + userModify.getUSER_ID();
		} else {

			System.out.println("変更成功！");
			
			mUserService.UserModifyService(user);
			
		}
		return "ModifyOK";
	}

	@GetMapping(value = "/GS/ModifyOK")
	private String ModifyOK(HttpSession session) {
		System.out.println("ユーザー編集完了画面");
		
		return "ModifyOK";
	}

	@GetMapping(value = "/GS/delete/{USER_ID}")
	private String UserDelete(HttpSession session, @PathVariable String USER_ID) throws Exception {
		mUserService.UserDeleteService(USER_ID);
		
		System.out.println(USER_ID + "削除完了");

		return "redirect:/GS/Userlist";
	}

	@GetMapping(value = "/GS/loginreset/{USER_ID}")
	private String UserReset(HttpSession session, @PathVariable String USER_ID, @RequestParam(value = "idx", required = false) Long idx) throws Exception {
		mUserService.UserResetService(USER_ID);

		System.out.println(USER_ID + "ログリンロック解除完了");

		return "redirect:/GS/Userlist";
	}

	@GetMapping("/error")
	private String error() {
		System.out.println("Error Out");

		System.out.println("不定アクセス発生");
		
		return "error";
	}

}
