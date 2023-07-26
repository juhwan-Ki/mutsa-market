package com.mutsa.market.service;

import com.mutsa.market.dto.SalesItemParameter;
import com.mutsa.market.dto.ResponseDTO;
import com.mutsa.market.dto.SalesItemDTO;
import com.mutsa.market.entity.SalesItem;
import com.mutsa.market.exception.ItemNotFoundException;
import com.mutsa.market.exception.PasswordException;
import com.mutsa.market.exception.UploadException;
import com.mutsa.market.repository.SalesItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Service
@RequiredArgsConstructor
public class SalesItemService {
    private final SalesItemRepository repository;

    // 상품 등록
    public ResponseDTO createItem(SalesItemDTO dto) {
        SalesItem entity = new SalesItem();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setMinPriceWanted(dto.getMinPriceWanted());
        entity.setWriter(dto.getWriter());
        entity.setPassword(dto.getPassword());
        entity.setStatus("판매중");
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
    public ResponseDTO updateItem(Long itemId, SalesItemDTO salesItemDTO) {
        SalesItem updateItem = repository.findById(itemId).orElseThrow(ItemNotFoundException::new);
        ResponseDTO response = new ResponseDTO();

        // 비밀번호가 맞으면 수정
        if(updateItem.getWriter().equals(salesItemDTO.getWriter()) && updateItem.getPassword().equals(salesItemDTO.getPassword())){
            updateItem.setTitle(salesItemDTO.getTitle());
            updateItem.setDescription(salesItemDTO.getDescription());
            updateItem.setMinPriceWanted(salesItemDTO.getMinPriceWanted());
            updateItem.setWriter(salesItemDTO.getWriter());
            updateItem.setPassword(salesItemDTO.getPassword());
            repository.save(updateItem);

            response.setMessage("물품이 수정되었습니다.");
        } else {
            throw new PasswordException("작성자 혹은 비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
        }
        return response;
    }

    // 상품 이미지 등록
    public ResponseDTO uploadItemImage(Long itemId, MultipartFile image, String writer, String password) {
        SalesItem findItem = repository.findById(itemId).orElseThrow(ItemNotFoundException::new);

        // 작성자나 비밀번호가 맞지 않으면 에러 발생
        if(!findItem.getWriter().equals(writer) || !findItem.getPassword().equals(password)){
            throw new PasswordException("작성자 혹은 비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
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
    public ResponseDTO deleteItem(Long itemId, SalesItemParameter salesItemParameter) {
        SalesItem findItem = repository.findById(itemId).orElseThrow(ItemNotFoundException::new);
        // 비밀번호가 맞으면 삭제
        if(findItem.getWriter().equals(salesItemParameter.getWriter()) && findItem.getPassword().equals(salesItemParameter.getPassword())){
            repository.delete(findItem);
        } else {
            throw new PasswordException("작성자 혹은 비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
        }

        ResponseDTO response = new ResponseDTO();
        response.setMessage("물품을 삭제했습니다.");
        return response;
    }

}
