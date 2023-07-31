package com.mutsa.market.service;

import com.mutsa.market.dto.ResponseDTO;
import com.mutsa.market.dto.SalesItemDTO;
import com.mutsa.market.entity.SalesItem;
import com.mutsa.market.entity.User;
import com.mutsa.market.exception.ItemNotFoundException;
import com.mutsa.market.exception.PasswordException;
import com.mutsa.market.exception.UploadException;
import com.mutsa.market.jwt.JwtValidationCheck;
import com.mutsa.market.repository.SalesItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SalesItemService {

    private final SalesItemRepository repository;
    private final JwtValidationCheck validationCheck;

    // 상품 등록
    @Transactional
    public ResponseDTO createItem(SalesItemDTO dto) {

        // JWT 체크하여 User리턴
        User user = validationCheck.userValidationCheck();

        SalesItem entity = new SalesItem();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setMinPriceWanted(dto.getMinPriceWanted());
        entity.setStatus("판매중");
        entity.setUser(user);
        repository.save(entity);

        ResponseDTO response = new ResponseDTO();
        response.setMessage("등록이 완료되었습니다.");
        return response;
    }

    // 상품 전체 조회(페이지 네이션)
    public Page<SalesItemDTO> readAllItems(Integer page, Integer limit){
        // 페이지 네이션을 위한 Pageable 객체 생성하여 페이지와 리미트, 정렬 정보 넣어줌
        Pageable pageable = PageRequest.of(page, limit, Sort.by("id"));
        Page<SalesItem> salesItems = repository.findAll(pageable);
        return salesItems.map(SalesItemDTO::fromEntity);
    }

    // 상품 단건 조회
    public SalesItemDTO readItem(Long itemId) {
        return SalesItemDTO.fromEntityOne(repository.findById(itemId).orElseThrow(ItemNotFoundException::new));
    }

    // 상품정보 변경
    @Transactional
    public ResponseDTO updateItem(Long itemId, SalesItemDTO salesItemDTO) {
        SalesItem updateItem = repository.findById(itemId).orElseThrow(ItemNotFoundException::new);
        ResponseDTO response = new ResponseDTO();

        // JWT 체크하여 User리턴
        User user = validationCheck.userValidationCheck();

        // 비밀번호가 맞으면 수정
        if(user.equals(updateItem.getUser())){
            updateItem.setTitle(salesItemDTO.getTitle());
            updateItem.setDescription(salesItemDTO.getDescription());
            updateItem.setMinPriceWanted(salesItemDTO.getMinPriceWanted());
            updateItem.setUser(user);
            repository.save(updateItem);

            response.setMessage("물품이 수정되었습니다.");
        } else {
            throw new PasswordException("작성자가 일치하지 않습니다. 다시 확인해주세요.");
        }
        return response;
    }

    // 상품 이미지 등록
    @Transactional
    public ResponseDTO uploadItemImage(Long itemId, MultipartFile image) {
        SalesItem findItem = repository.findById(itemId).orElseThrow(ItemNotFoundException::new);

        // JWT 체크하여 User리턴
        User user = validationCheck.userValidationCheck();

        // 작성자나 비밀번호가 맞지 않으면 에러 발생
        if(!user.equals(findItem.getUser())){
            throw new PasswordException("작성자가 일치하지 않습니다. 다시 확인해주세요.");
        }

        // 폴더 생성
        String imgDir = String.format("img/%d/", itemId);
        try {
            Files.createDirectories(Path.of(imgDir));
        } catch (IOException e){
            throw new UploadException();
        }

        String originName = image.getOriginalFilename();
        String path = imgDir + originName;

        // 이미지 저장
        try {
            image.transferTo(Path.of(path));
        } catch (IOException e) {
            throw new UploadException();
        }

        // 이미지 url 저장
        findItem.setImageUrl(imgDir + originName);
        repository.save(findItem);

        ResponseDTO response = new ResponseDTO();
        response.setMessage("이미지가 등록되었습니다.");
        return response;
    }


    // 상품 삭제
    @Transactional
    public ResponseDTO deleteItem(Long itemId) {
        SalesItem findItem = repository.findById(itemId).orElseThrow(ItemNotFoundException::new);

        // JWT 체크하여 User리턴
        User user = validationCheck.userValidationCheck();

        // 비밀번호가 맞으면 삭제
        if(user.equals(findItem.getUser())){
            repository.delete(findItem);
        } else {
            throw new PasswordException("작성자가 일치하지 않습니다. 다시 확인해주세요.");
        }

        ResponseDTO response = new ResponseDTO();
        response.setMessage("물품을 삭제했습니다.");
        return response;
    }

}
