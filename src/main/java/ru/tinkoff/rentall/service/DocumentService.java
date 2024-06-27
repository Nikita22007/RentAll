package ru.tinkoff.rentall.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.rentall.document.DocumentGenerator;
import ru.tinkoff.rentall.dto.DocumentDTO;
import ru.tinkoff.rentall.entity.Advertisement;
import ru.tinkoff.rentall.entity.User;
import ru.tinkoff.rentall.repository.AdvertisementRepository;
import ru.tinkoff.rentall.repository.UserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class DocumentService {

    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;

    public byte[] generateDocuments(DocumentDTO documentDTO) throws IOException {
        Optional<User> lesseeUserOpt = userRepository.findById(documentDTO.getSessionUserLogin());
        Optional<Advertisement> adOpt = advertisementRepository.findById(documentDTO.getAdvId());

        if (lesseeUserOpt.isPresent() && adOpt.isPresent()) {
            User lessorUser = adOpt.get().getUser();
            String lessorName = lessorUser.getUserFullName();
            String lessorAddress = lessorUser.getUserAddress();
            String lessorNumber = lessorUser.getPhoneNumber();
            String lesseeName = lesseeUserOpt.get().getUserFullName();
            String lesseeAddress = lesseeUserOpt.get().getUserAddress();
            String lesseeNumber = lesseeUserOpt.get().getPhoneNumber();
            String productName = adOpt.get().getAdvName();
            String productDescription = adOpt.get().getDescription();
            String rentalTime = String.valueOf(adOpt.get().getRentTime());
            String rentalPrice = adOpt.get().getAdvPrice();
            String timeUnit = adOpt.get().getTimeUnit();

            Map<String, String> replacements = new HashMap<>();
            replacements.put("{LESSOR_NAME}", lessorName);
            replacements.put("{LESSEE_NAME}", lesseeName);
            replacements.put("{LESSOR_ADDRESS}", lessorAddress);
            replacements.put("{LESSEE_ADDRESS}", lesseeAddress);
            replacements.put("{LESSOR_NUMBER}", lessorNumber);
            replacements.put("{LESSEE_NUMBER}", lesseeNumber);
            replacements.put("{PRODUCT_NAME}", productName);
            replacements.put("{PRODUCT_DESCRIPTION}", productDescription);
            replacements.put("{RENTAL_TIME}", rentalTime);
            replacements.put("{RENTAL_PRICE}", rentalPrice);
            replacements.put("{TIME_UNIT}", timeUnit);


            DocumentGenerator docGen = new DocumentGenerator();
            String outputName = "src/main/resources/documents/" + lessorName + "_" + lesseeName + "_" + productName + ".docx";
            docGen.createDocxFromTemplate("src/main/resources/documents/template.docx", outputName, replacements);

            return Files.readAllBytes(Paths.get(outputName));
        }
        return new byte[0];
    }
}
