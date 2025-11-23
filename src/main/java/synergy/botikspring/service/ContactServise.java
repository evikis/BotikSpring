package synergy.botikspring.service;

import synergy.botikspring.dto.ContactDto;

import java.util.List;

public interface ContactServise {
    List<ContactDto> findAll();
    ContactDto findById(Long id) throws Exception;
    ContactDto create(ContactDto contactDto) throws Exception;
    ContactDto update(ContactDto dto,  Long id) throws Exception;
    void delete(Long id);
    ContactDto update(ContactDto dto);
}

