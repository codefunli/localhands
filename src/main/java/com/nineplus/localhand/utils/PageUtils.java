package com.nineplus.localhand.utils;

import com.nineplus.localhand.dto.ContentPageDto;
import com.nineplus.localhand.dto.PageResDto;
import com.nineplus.localhand.dto.PageSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PageUtils {
    @Autowired
    LocalHandBeanUtil beanUtils;

    @Autowired
    ConvertResponseUtils convertResponseUtils;

    /**
     * convert page to page response DTO
     *
     * @param page
     * @param dtoClass
     * @return PageResponseDTO<DTO>
     */
    public <D, E> PageResDto<D> convertPageEntityToDTO(Page<E> page, Class<D> dtoClass) {
        PageResDto<D> pageResDTO = new PageResDto<>();
        ContentPageDto rPageDTO = new ContentPageDto();
        rPageDTO.setTotalPages(page.getTotalPages());
        rPageDTO.setTotalElements(page.getTotalElements());
        rPageDTO.setSize(page.getSize());
        rPageDTO.setNumber(page.getNumber());

        pageResDTO.setMetaData(rPageDTO);
        pageResDTO.setContent(beanUtils.copyListToList(page.getContent(), dtoClass));

        return pageResDTO;
    }

    public Pageable convertSearch(PageSearchDto pageSearchDto) {
        String mappedColumn = convertResponseUtils.convertResponseDocument(pageSearchDto.getSortBy());
        return PageRequest.of(Integer.parseInt(pageSearchDto.getPage()), Integer.parseInt(pageSearchDto.getSize()),
                Sort.by(pageSearchDto.getSortDirection(), mappedColumn));
    }

}
