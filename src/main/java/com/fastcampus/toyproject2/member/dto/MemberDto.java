package com.fastcampus.toyproject2.member.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MemberDto {
    private Integer member_number;
    private String member_state_code;
    private String id;
    private String password;
    private String name;
    private String birth;
    private String sex;
    private String phone_number;
    private String email;
    private String login_datetime;
    private String modify_datetime;
    private String register_datetime;
    private String exit_datetime;
    private String recommand_id;
    private Integer login_count;
    private String is_admin;
    private String note;

    public MemberDto(Integer member_number, String member_state_code, String id) {
        this.member_number = member_number;
        this.member_state_code = member_state_code;
        this.id = id;
    }

    public MemberDto(Integer member_number, String member_state_code, String id, String password) {
        this.member_number = member_number;
        this.member_state_code = member_state_code;
        this.id = id;
        this.password = password;
    }

    /* 회원 가입 */
    public MemberDto(String member_state_code, String id, String password,
                     String name, String birth, String sex, String phone_number, String email,
                     String recommand_id, String is_admin) {
        this.member_state_code = member_state_code;
        this.id = id;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.sex = sex;
        this.phone_number = phone_number;
        this.email = email;
        this.recommand_id = recommand_id;
        this.is_admin = is_admin;
    }


    /* 전체 회원 정보 조회 및 수정  */
    public MemberDto(Integer member_number, String member_state_code, String id, String password,
                     String name, String birth, String sex, String phone_number, String email,
                     String login_datetime, String modify_datetime, String register_datetime, String exit_datetime,
                     String recommand_id, Integer login_count, String is_admin, String note) {
        this.member_number = member_number;
        this.member_state_code = member_state_code;
        this.id = id;
        this.password = password;
        this.name = name;
        this.birth = birth;
        this.sex = sex;
        this.phone_number = phone_number;
        this.email = email;
        this.login_datetime = login_datetime;
        this.modify_datetime = modify_datetime;
        this.register_datetime = register_datetime;
        this.exit_datetime = exit_datetime;
        this.recommand_id = recommand_id;
        this.login_count = login_count;
        this.is_admin = is_admin;
        this.note = note;
    }

    public Integer getMember_number() {
        return member_number;
    }

    public void setMember_number(Integer member_number) {
        this.member_number = member_number;
    }

    public String getMember_state_code() {
        return member_state_code;
    }

    public void setMember_state_code(String member_state_code) {
        this.member_state_code = member_state_code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin_datetime() {
        return login_datetime;
    }

    public void setLogin_datetime(String login_datetime) {
        this.login_datetime = login_datetime;
    }

    public String getModify_datetime() {
        return modify_datetime;
    }

    public void setModify_datetime(String modify_datetime) {
        this.modify_datetime = modify_datetime;
    }

    public String getRegister_datetime() {
        return register_datetime;
    }

    public void setRegister_datetime(String register_datetime) {
        this.register_datetime = register_datetime;
    }

    public String getExit_datetime() {
        return exit_datetime;
    }

    public void setExit_datetime(String exit_datetime) {
        this.exit_datetime = exit_datetime;
    }

    public String getRecommand_id() {
        return recommand_id;
    }

    public void setRecommand_id(String recommand_id) {
        this.recommand_id = recommand_id;
    }

    public Integer getLogin_count() {
        return login_count;
    }

    public void setLogin_count(Integer login_count) {
        this.login_count = login_count;
    }

    public String getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(String is_admin) {
        this.is_admin = is_admin;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "MemberDto{" +
                "member_number=" + member_number +
                ", member_state_code='" + member_state_code + '\'' +
                ", id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", birth=" + birth +
                ", sex='" + sex + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", email='" + email + '\'' +
                ", login_datetime=" + login_datetime +
                ", modify_datetime=" + modify_datetime +
                ", register_datetime=" + register_datetime +
                ", exit_datetime=" + exit_datetime +
                ", recommand_id='" + recommand_id + '\'' +
                ", login_count=" + login_count +
                ", is_admin='" + is_admin + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
