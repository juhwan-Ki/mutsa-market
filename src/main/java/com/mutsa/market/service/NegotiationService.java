package com.mutsa.market.service;

import com.mutsa.market.dto.NegotiationDTO;
import com.mutsa.market.dto.NegotiationParameter;
import com.mutsa.market.dto.ResponseDTO;
import com.mutsa.market.entity.Negotiation;
import com.mutsa.market.entity.SalesItem;
import com.mutsa.market.exception.ItemNotFoundException;
import com.mutsa.market.exception.ItemStatusException;
import com.mutsa.market.exception.PasswordException;
import com.mutsa.market.exception.ProposalNotFoundException;
import com.mutsa.market.repository.NegotiationRepository;
import com.mutsa.market.repository.SalesItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NegotiationService {

    private final SalesItemRepository salesItemRepository;
    private final NegotiationRepository negotiationRepository;

    // 구매제안 등록
    public ResponseDTO createProposal(Long itemId, NegotiationParameter parameter) {
        SalesItem findItem = salesItemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);
        // 해당 상품의 상태가 판매중일 경우에만 구매 제안을 등록하도록 함
        if(findItem.getStatus().equals("판매중")) {
            Negotiation entity = new Negotiation();
            entity.setSalesItem(findItem);
            entity.setWriter(parameter.getWriter());
            entity.setPassword(parameter.getPassword());
            entity.setSuggestedPrice(parameter.getSuggestedPrice());
            entity.setStatus("제안");
            negotiationRepository.save(entity);
        } else {
            throw new ItemStatusException();
        }

        ResponseDTO response = new ResponseDTO();
        response.setMessage("구매 제안이 등록되었습니다.");
        return response;
    }

    // 구매 제안 조회
    public Page<NegotiationDTO> readAllProposal(Long itemId, String writer, String password, Integer page) {

        SalesItem findItem = salesItemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);
        Pageable pageable = PageRequest.of(page, 25, Sort.by("id"));

        Page<Negotiation> negotiationPage;
        // 물품 등록자 이면
        if(findItem.getWriter().equals(writer) && findItem.getPassword().equals(password)) {
            negotiationPage = negotiationRepository.findAllBySalesItem(findItem, pageable);
        }
        // 구매 제안자 이면
        else {
            negotiationPage = negotiationRepository.findAllBySalesItemAndWriterAndPassword(findItem, writer, password, pageable);
        }

        return negotiationPage.map(NegotiationDTO::fromEntity);
    }

    // 수정 분기처리 메소드
    public ResponseDTO updateProposal(Long itemId, Long proposalId, NegotiationParameter parameter) {

        SalesItem findItem = salesItemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);

        // API의 url이 같기 때문에 분기처리 하여 서비스를 호출
        if(parameter.getSuggestedPrice() != null){
            return updateProposalSuggestPrice(itemId, proposalId, parameter, findItem);
        } else {
            return updateProposalStatus(itemId, proposalId, parameter, findItem);
        }
    }

    // 구매 제안 삭제
    public ResponseDTO deleteProposal(Long itemId, Long proposalId, NegotiationParameter parameter) {

        SalesItem findItem = salesItemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);
        Negotiation findProposal = negotiationRepository.findById(proposalId).orElseThrow(ProposalNotFoundException::new);

        // 해당 상품이 판매중이 아니면 에러 발생
        if(!findItem.getStatus().equals("판매중")) {
            throw new ItemStatusException();
        }

        // 작성자와 비밀번호가 맞을 때
        if(findProposal.getWriter().equals(parameter.getWriter()) && findProposal.getPassword().equals(parameter.getPassword())){
            negotiationRepository.delete(findProposal);
        } else {
            throw new PasswordException("작성자 혹은 비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
        }

        ResponseDTO response = new ResponseDTO();
        response.setMessage("제안을 삭제했습니다.");
        return response;
    }

    // 구매 제안 가격 수정
    public ResponseDTO updateProposalSuggestPrice(Long itemId, Long proposalId, NegotiationParameter parameter, SalesItem findItem){
        Negotiation findProposal = negotiationRepository.findById(proposalId).orElseThrow(ProposalNotFoundException::new);

        // 해당 상품이 판매중이 아니면 에러 발생
        if(!findItem.getStatus().equals("판매중")) {
            throw new ItemStatusException();
        }

        // 작성자와 비밀번호가 맞을 때
        if(findProposal.getWriter().equals(parameter.getWriter()) && findProposal.getPassword().equals(parameter.getPassword())){
            findProposal.setSuggestedPrice(parameter.getSuggestedPrice());
            negotiationRepository.save(findProposal);
        } else {
            throw new PasswordException("작성자 혹은 비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
        }

        ResponseDTO response = new ResponseDTO();
        response.setMessage("제안이 수정되었습니다.");
        return response;
    }

    // 구매 제안 상태 변경
    public ResponseDTO updateProposalStatus(Long itemId, Long proposalId, NegotiationParameter parameter, SalesItem findItem) {

        Negotiation findProposal = negotiationRepository.findById(proposalId).orElseThrow(ProposalNotFoundException::new);
        ResponseDTO response = new ResponseDTO();

        // 해당 상품이 판매중이 아니면 에러 발생
        // 판매 완료에서 다시 판매중으로 변경할 수 없다고 가정함
        if(!findItem.getStatus().equals("판매중")) {
            throw new ItemStatusException();
        }

        // 작성자와 비밀번호가 맞을 때
        if(findItem.getWriter().equals(parameter.getWriter()) && findItem.getPassword().equals(parameter.getPassword())){
            findProposal.setStatus(parameter.getStatus());
            negotiationRepository.save(findProposal);
            response.setMessage("제안의 상태가 변경되었습니다.");
        } else if(
                findProposal.getWriter().equals(parameter.getWriter())
                && findProposal.getPassword().equals(parameter.getPassword())
                && findProposal.getStatus().equals("수락")
        ) {
            // 상태가 수락일 경우 구매 제안 확정으로 변경
            findProposal.setStatus(parameter.getStatus());
            negotiationRepository.save(findProposal);
            // 구매 제안이 확정된 경우 해당 물품의 상태는 판매 완료로 변경
            findItem.setStatus("판매 완료");
            salesItemRepository.save(findItem);
            // 구매 제안이 확정된 경우 확정되지 않은 다른 구매 제안의 상태는 모두 거절로 변경
            List<Negotiation> itemList = negotiationRepository.findAllBySalesItemAndIdNot(findItem, proposalId);
            for (Negotiation negotiation : itemList) {
                negotiation.setStatus("거절");
            }
            negotiationRepository.saveAll(itemList);
            response.setMessage("구매가 확정되었습니다.");
        } else {
            throw new PasswordException("작성자 혹은 비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
        }

        return response;
    }
}
