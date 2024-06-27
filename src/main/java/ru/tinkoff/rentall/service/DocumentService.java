package ru.tinkoff.rentall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.rentall.document.DocumentGenerator;
import ru.tinkoff.rentall.dto.DocumentDTO;
import ru.tinkoff.rentall.entity.Advertisement;
import ru.tinkoff.rentall.entity.RentHistory;
import ru.tinkoff.rentall.entity.User;
import ru.tinkoff.rentall.repository.AdvertisementRepository;
import ru.tinkoff.rentall.repository.RentHistoryRepository;
import ru.tinkoff.rentall.repository.UserRepository;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalTime;

@Service @RequiredArgsConstructor
public class DocumentService {

    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;
    private final RentHistoryRepository rentHistoryRepository;

    public String generateDocuments(DocumentDTO documentDTO) throws IOException {
        Optional<RentHistory> rentOpt = rentHistoryRepository.findById(documentDTO.getRentId());

        if (rentOpt.isPresent()) {
            Advertisement advertisement = rentOpt.get().getAdvertisement();
            User lesseeUser = rentOpt.get().getUser();
            User lessorUser = advertisement.getUser();
            String lessorName = lessorUser.getUserFullName();
            String lessorAddress = lessorUser.getUserAddress();
            String lessorNumber = lessorUser.getPhoneNumber();
            String lesseeName = lesseeUser.getUserFullName();
            String lesseeAddress = lesseeUser.getUserAddress();
            String lesseeNumber = lesseeUser.getPhoneNumber();
            String productName = advertisement.getAdvName();
            String productDescription = advertisement.getDescription();
            LocalDate date = rentOpt.get().getStartDateTime().toLocalDate();
            LocalTime time = rentOpt.get().getStartDateTime().toLocalTime().withSecond(0).withNano(0);
            String startDateTime = date + " " + time;
            date = rentOpt.get().getEndDateTime().toLocalDate();
            time = rentOpt.get().getEndDateTime().toLocalTime().withSecond(0).withNano(0);
            String endDateTime = date + " " + time;
            String rentalPrice = advertisement.getAdvPrice();
            String timeUnit = advertisement.getTimeUnit();

            Map<String, String> replacements = new HashMap<>();
            replacements.put("{LESSOR_NAME}", lessorName);
            replacements.put("{LESSEE_NAME}", lesseeName);
            replacements.put("{LESSOR_ADDRESS}", lessorAddress);
            replacements.put("{LESSEE_ADDRESS}", lesseeAddress);
            replacements.put("{LESSOR_NUMBER}", lessorNumber);
            replacements.put("{LESSEE_NUMBER}", lesseeNumber);
            replacements.put("{PRODUCT_NAME}", productName);
            replacements.put("{PRODUCT_DESCRIPTION}", productDescription);
            replacements.put("{START_DATE_TIME}", startDateTime);
            replacements.put("{END_DATE_TIME}", endDateTime);
            replacements.put("{RENTAL_PRICE}", rentalPrice);
            replacements.put("{TIME_UNIT}", timeUnit);


            DocumentGenerator docGen = new DocumentGenerator();
            String outputName = "src/main/resources/documents/" + lessorName + "_" + lesseeName + "_" + productName + ".docx";
            docGen.createDocxFromTemplate("src/main/resources/documents/template.docx", outputName, replacements);

            return outputName;
        }
        return "error";
    }
}
