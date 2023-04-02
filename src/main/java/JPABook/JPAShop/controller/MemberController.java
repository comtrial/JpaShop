package JPABook.JPAShop.controller;

import JPABook.JPAShop.controller.form.MemberForm;
import JPABook.JPAShop.domain.Address;
import JPABook.JPAShop.domain.Member;
import JPABook.JPAShop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class MemberController {

    @Autowired
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm(Model model) { //controller 에서 view 로 넘어갈 때 model 로 데이터를 실어 나른다
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm.html";
    }

    //위의 Get 과 경로가 같지만 URI 를 리소스 접근 주소,
    //http method 를 리소스에서 수행하고자 하는 행위 관점으로 보면 이해가 쉽ㅈ다.
    @PostMapping("/members/new")
    public String createMember(@Valid MemberForm memberForm, BindingResult result) {
        // 1. @Valid 를 통해 MemberForm 에 @NotEmpty 와 같은 값의 유효성 검사를 진행해 준다.
        // 2. MemberForm 의 사용이유: 굳이 Member 가 아닌 MemberForm 을 사용하는 이유는
        //    * 도메인 엔티티를 더럽히지 않기 위해서
        //    * 추가적인 검증 로직과 같은 Controller 에서의 로직이 포함 되는 것이 도메인 엔티티의 Member 와 MemberForm 과는 차이가 있다.
        // 3. BindingResult: memberform 에 붙어 있는 @Valid 에서 에러가 발생시 기존에는 Controller 로 넘어가지 않고 튕겨 버리는데
        // 해당 키워드를 통해서 오류가 result 에 담겨서 Controller 내부의 코드가 동작하게 된다. (BindingResult 없으면 아래 코드가 돌지도 않음
        //<input type="text" th:field="*{name}" class="form-control" placeholder="이름을 입력하세요"
        //   th:class="${#fields.hasErrors('name')}? 'form-control fieldError' : 'form-control'">
        //<p th:if="${#fields.hasErrors('name')}" th:errors="*{name}">Incorrect date</p>

        // System.out.println("!! @Valid err 시 Controller 넘어오는지 확인");

        if (result.hasErrors()) { return "members/createMemberForm.html"; }

        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());

        Member member = new Member();
        member.setName(memberForm.getName());
        member.setAddress(address);

        memberService.join(member);
        System.out.println(member);

        return "redirect:/"; //재로딩을 방지 하기 위해서 redirect 로 home 으로 보내버린다.
    }
}
