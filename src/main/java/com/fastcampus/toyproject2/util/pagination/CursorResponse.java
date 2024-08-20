package com.fastcampus.toyproject2.util.pagination;

import java.util.List;

public record CursorResponse<T> (
        CursorRequest nextCursorRequest,    // 다음 요청
        List<T> body    // 불러온 contents
){ }