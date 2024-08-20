package com.fastcampus.toyproject2.Notice.service;

import com.fastcampus.toyproject2.Notice.dto.NoticeDto;
import com.fastcampus.toyproject2.Notice.repository.NoticeDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

//트랜잭션 처리
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeDao noticeDao;

    //1.저장 insert()
    @Override
    public int insert(NoticeDto noticeDto) throws Exception{
        //체크박스 체크 안하면 is_top_post N으로 설정
        if(noticeDto.getIs_top_post() == null){
            noticeDto.setIs_top_post("N");
        }
        return noticeDao.insert(noticeDto);
    }

    //2. 공지 상세 -  selectById()
    @Override
    public NoticeDto selectById(Long id) throws Exception {
        return noticeDao.selectById(id);
    }

    //3. 공지 리스트 전체 내림차순 페이지네이션 - selectAllDescPage()
    @Override
    public List<NoticeDto> selectAllDescPage(Map map) throws Exception{
        return noticeDao.selectAllDescPage(map);
    }

    //4. 공지 상단 게시 리스트 내림차순 - selectAllTopPost()
    @Override
    public List<NoticeDto> selectAllTopPost() throws Exception{
        return noticeDao.selectAllTopPost();
    }

    //4-1. 테스트용 공지 리스트 내림차순
    @Override
    public List<NoticeDto> selectAllAsc() throws Exception{
        return noticeDao.selectAllAsc();
    }

    //5. 수정 - update() - 제목, 내용,
    @Override
    public int update(NoticeDto noticeDto) throws Exception{
        return noticeDao.update(noticeDto);
    }

    //5. 삭제 - is_post를 "N"으로 변경하는 update()
    @Override
    public int delete(Long id) throws Exception {
        //해당 id로 글 찾고
        NoticeDto selectDto = noticeDao.selectById(id);
        //updateDto 생성
        //TODO : 삭제한 관리자의 id 받아와서 매개변수로 주기
        String nowStr = LocalDateTime.now().toString(); //현재시간 생성
        NoticeDto updateDto = new NoticeDto(selectDto.getNotice_id(), selectDto.getNotice_title(), selectDto.getNotice_content(), nowStr, selectDto.getModifier(), selectDto.getIs_top_post(), "N");
        return noticeDao.update(updateDto);
    }

    //6. 공지 게시글 수 count
    @Override
    public int getCount() throws Exception {
        return noticeDao.count();
    }

}
