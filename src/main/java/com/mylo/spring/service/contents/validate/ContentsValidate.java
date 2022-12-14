package com.mylo.spring.service.contents.validate;

import com.mylo.common.core.exception.MyloCommonException;
import com.mylo.domain.contents.repository.ContentsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import static com.mylo.common.core.exception.ErrorType.ERROR_CONTENTS;
import static com.mylo.common.core.exception.ServiceStatusCode.*;

@Slf4j
@Service
public class ContentsValidate {

    @Autowired ContentsMapper contentsMapper;

    /**
     * insert, update, delete 시 'cv_contents_meta' table의 row 유무를 판단하는 메소드
     * @param contentsIdx
     * @param compare
     */
    public void existMeta(Integer contentsIdx, int compare) {
        if(compare == 0) { // update, delete 시 존재 유무(compare == 0)
            if (contentsMapper.existMeta(contentsIdx) == compare) {
                throw new MyloCommonException(ERROR_CONTENTS, ERROR_NOT_EXIST_ROW, "Not exist meta contentsIdx : " + contentsIdx + "!!");
            }
        } else{ // insert 시 존재 유무(compare == 1)
            if (contentsMapper.existMeta(contentsIdx) == compare) {
                throw new MyloCommonException(ERROR_CONTENTS, ERROR_ALREADY_EXIST_ROW, "Already exist meta contentsIdx : " + contentsIdx + "!!");
            }
        }
    }

    /**
     * insert, update, delete 시 'cv_contents' table의 row 유무를 판단하는 메소드
     * @param contentsIdx
     */
    public void existFile(Integer contentsIdx) { // DB의 file 데이터 유무
        if(contentsMapper.existFile(contentsIdx) == 0){
            throw new MyloCommonException(ERROR_CONTENTS, ERROR_NOT_EXIST_ROW, "There is no idx for file : " + contentsIdx + "!!");
        }
    }

    /**
     * idx의 유효성 판단
     * null 또는 0 검사
     * @param idx
     */
    public void paramValidation(Integer idx) { // idx 유효성 확인
        if (null == idx || 0 == idx) {
            throw new MyloCommonException(ERROR_CONTENTS, ERROR_PARAM_VALIDITY, "Invalid idx :" + idx + "!!");
        }
    }

    /**
     * idx, metaJson의 유효성 판단
     * idx -> null 또는 0 검사
     * meatJson -> ObjectUtils.isEmpty()로 빈값 검사
     * @param idx
     * @param metaJson
     */
    public void paramValidation(Integer idx, Object metaJson) { // idx, metaJson 유효성 확인
        if (null == idx || 0 == idx) { // idx 유효성 확인
            throw new MyloCommonException(ERROR_CONTENTS, ERROR_PARAM_VALIDITY, "Invalid idx :" + idx + "!!");
        }
        if (ObjectUtils.isEmpty(metaJson)){ // metaJson 유효성 확인
            throw new MyloCommonException(ERROR_CONTENTS, ERROR_PARAM_VALIDITY, "There is anything in metaJson !!");
        }
    }

    /**
     * insert 에러 발생
     */
    public void insertFail(){ // insert 실패

        throw new MyloCommonException(ERROR_CONTENTS, ERROR_INSERT, "Insert fail !!");
    }

    /**
     * update 에러 발생
     */
    public void updateFail(){ // update 실패

        throw new MyloCommonException(ERROR_CONTENTS, ERROR_UPDATE, "Update fail !!");
    }

    /**
     * delete 에러 발생
     */
    public void deleteFail(){ // delete 실패

        throw new MyloCommonException(ERROR_CONTENTS, ERROR_DELETE, "Delete fail !!");
    }

    /**
     * upload 에러 발생
     */
    public void uploadFail(){ // upload 실패
        throw new MyloCommonException(ERROR_CONTENTS, ERROR_UPLOAD_FAIL, "Upload fail !!");
    }
}
