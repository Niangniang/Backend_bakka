package org.bakka.userservice.RecodDtos;

import java.util.UUID;

public record ChangerPlafondRequestDto(UUID compteBakkaId, UUID plafondId) {
}
