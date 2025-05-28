package com.marcus.purchase.infrastructure.controller;

import com.marcus.pagination.domain.model.PageRequest;
import com.marcus.pagination.domain.model.Pageable;
import com.marcus.purchase.domain.model.Purchase;
import com.marcus.purchase.domain.service.PurchaseService;
import com.marcus.purchase.infrastructure.controller.in.PurchaseRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase")
@RequiredArgsConstructor
@Tag(name = "Purchase endpoints")
@PreAuthorize("hasRole('USER')")
public class PurchaseController {

  private final PurchaseService purchaseService;

  @PostMapping("/search")
  @Operation(
      summary = "This will retrieve all the purchases done by user",
      description =
          """
    As said in the description, this will retrieve a paginated response for the users purchases.
    """)
  public ResponseEntity<Pageable<Purchase>> getPurchases(@RequestBody PageRequest pageRequest) {

    return ResponseEntity.ok(purchaseService.search(pageRequest));
  }

  @PostMapping
  @Operation(
      summary = "Create a purchase",
      description =
          """
        This will create from the product id's passed by a purchase with all of them, there are validations, such as:

                · Products can not differ category.
                · Neither subcategory, as this also limits how you purchase something
                · Category has to be enabled, if not an exception will be thrown
        """)
  public ResponseEntity<Purchase> createPurchase(@RequestBody @Valid PurchaseRequest request) {

    return ResponseEntity.ok(purchaseService.createPurchase(request));
  }
}
