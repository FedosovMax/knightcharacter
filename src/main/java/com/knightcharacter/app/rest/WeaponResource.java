package com.knightcharacter.app.rest;

import static com.knightcharacter.app.Constants.API_BASE_ITEMS;
import static com.knightcharacter.app.Constants.API_BASE_WEAPONS;

import com.knightcharacter.app.domain.WeaponVO;
import com.knightcharacter.app.rest.mappers.WeaponRestMapper;
import com.knightcharacter.app.rest.request.WeaponRequestDto;
import com.knightcharacter.app.rest.response.WeaponResponseDto;
import com.knightcharacter.app.service.WeaponService;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(API_BASE_ITEMS + API_BASE_WEAPONS)
@Slf4j
public class WeaponResource {

    private final WeaponService weaponService;
    private final WeaponRestMapper weaponRestMapper;

    @PostMapping
    public ResponseEntity<WeaponResponseDto> addWeapon(@Valid @RequestBody WeaponRequestDto requestDto) {
        log.info("Request to add weapon : {}", requestDto);
        WeaponVO weaponVO = weaponRestMapper.toWeaponVO(requestDto);
        WeaponVO savedWeaponVO = weaponService.save(weaponVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(weaponRestMapper.toWeaponResponseDto(savedWeaponVO));
    }

    @GetMapping
    public ResponseEntity<List<WeaponResponseDto>> getAllWeapons() {
        log.info("Request to get all weapons");
        return ResponseEntity.status(HttpStatus.FOUND)
            .body(weaponService.findAll()
                .stream()
                .map(weaponRestMapper::toWeaponResponseDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{weaponId}")
    public ResponseEntity<WeaponResponseDto> getWeaponById(@PathVariable String weaponId) {
        log.info("Request to get weapon by id : {}", weaponId);
        WeaponVO weaponVO = weaponService.findById(weaponId);
        return ResponseEntity.status(HttpStatus.FOUND).body(weaponRestMapper.toWeaponResponseDto(weaponVO));
    }

    @PutMapping("/{weaponId}")
    public ResponseEntity<WeaponResponseDto> updateWeapon(@PathVariable String weaponId,
        @Valid @RequestBody WeaponRequestDto requestDto) {
        log.info("Request to update weapon : {}", requestDto);
        WeaponVO weaponVO = weaponRestMapper.toWeaponVO(requestDto);
        WeaponVO updatedWeaponVO = weaponService.updateWeapon(weaponId, weaponVO);
        return ResponseEntity.ok().body(weaponRestMapper.toWeaponResponseDto(updatedWeaponVO));
    }

    @DeleteMapping("/{weaponId}")
    public ResponseEntity<Void> deleteWeapon(@PathVariable String weaponId) {
        log.info("Request to delete weapon by id : {}", weaponId);
        weaponService.deleteById(weaponId);
        return ResponseEntity.ok().build();
    }
}
