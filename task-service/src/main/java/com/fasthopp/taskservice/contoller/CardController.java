package com.fasthopp.taskservice.contoller;

import com.fasthopp.taskservice.dto.CardDto;
import com.fasthopp.taskservice.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("task/card")
@Tag(name = "Card Endpoints")
public class CardController {

    private final CardService cardService;
    private static final Logger logger = LoggerFactory.getLogger(CardController.class);

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @Operation(
            description = "Get endpoint for get all cards in a board's column.",
            summary = "Get all cards in board's column.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @GetMapping("/cards/{boardId}")
    public List<CardDto> getAllCards(@PathVariable Long boardId){
        return cardService.getAllCards(boardId);
    }

    @Operation(
            description = "Get endpoint for get a card by card ID.",
            summary = "get a card by card ID.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @GetMapping("/{cardId}")
    public CardDto getCardById(@PathVariable Long cardId) {
        return cardService.getCardById(cardId);
    }

    @Operation(
            description = "Post endpoint for creating a new card.",
            summary = "Create a new card.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @PostMapping("/create")
    public CardDto createCard(@RequestBody CardDto cardDto) {
        return cardService.createCard(cardDto);
    }

    @Operation(
            description = "Patch endpoint for update an already existing card.",
            summary = "Update a already existing card.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @PatchMapping("/update/{cardId}")
    public CardDto updateCard(@PathVariable Long cardId, @RequestBody CardDto cardDTO) {
        logger.info("Received update request for cardId: {} with data: {}", cardId, cardDTO);
        return cardService.updateCard(cardId, cardDTO);
    }

    @Operation(
            description = "Patch endpoint for update a column when a card is updated/created.",
            summary = "Update a column when a card is updated/created.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @PatchMapping("/update-column/{cardId}")
    public CardDto updateCardBoardColumn(@PathVariable Long cardId, @RequestParam Long newBoardColumnId) {
        System.out.println(newBoardColumnId + "params");
        return cardService.updateCardBoardColumn(cardId, newBoardColumnId);
    }

    @Operation(
            description = "Delete endpoint for delete an existing card in a column.",
            summary = "Delete an existing card in a column.",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @DeleteMapping("/{cardId}")
    public ResponseEntity<String> deleteCard(@PathVariable Long cardId) {
        try {
            cardService.deleteCard(cardId);
            return ResponseEntity.ok("Card deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body("Card not found");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
}
