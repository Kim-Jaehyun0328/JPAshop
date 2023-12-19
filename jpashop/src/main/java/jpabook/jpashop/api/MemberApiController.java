package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController //@Controller + @ResponseBody
@RequiredArgsConstructor
public class MemberApiController {
  private final MemberService memberService;

  @GetMapping("/api/v1/members")
  public List<Member> membersV1(){
    return memberService.findMembers();
  }

  @GetMapping("/api/v2/members")
  public Result memberV2() {
    List<Member> findMembers = memberService.findMembers();
    List<MemberDto> collect = findMembers.stream()
        .map(m -> new MemberDto(m.getName()))
        .collect(Collectors.toList());
    Result collects = new Result(collect);
    return collects;
  }

  @PostMapping("/api/v1/members")
  public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
    Long id = memberService.join(member);
    return new CreateMemberResponse(id);
  }

  @PostMapping("/api/v2/members")
  public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
    Member member = new Member();
    member.setName(request.getName());

    Long id = memberService.join(member);
    return new CreateMemberResponse(id);
  }

  @PatchMapping("/api/v2/members/{id}") //patch가 부분 업데이트, put은 전체 업데이트에 사용하는 것이 rest 스타일에 맞다.
  public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id, @RequestBody @Valid UpdateMemberRequest request) {
    memberService.update(id, request.getName());
    Member member = memberService.findOne(id);
    return new UpdateMemberResponse(member.getId(), member.getName());
  }


  @Data
  static class CreateMemberRequest {
    @NotEmpty(message = "이름을 입력하시오.")
    private String name;
  }

  @Data
  static class CreateMemberResponse {
    private Long id;

    public CreateMemberResponse(Long id) {
      this.id = id;
    }
  }

  @Data
  static class UpdateMemberRequest {
    private String name;
  }

  @Data
  @AllArgsConstructor
  static class UpdateMemberResponse {
    private Long id;
    private String name;
  }

  @Data
  @AllArgsConstructor
  static class Result<T> {
    private T data;
  }

  @Data
  @AllArgsConstructor
  static class MemberDto {
    private String name;
  }
}
