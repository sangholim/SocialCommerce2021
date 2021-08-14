package com.toy.project.client.product;

import com.toy.project.service.dto.PackageDescriptionDTO;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

public class PackageDescriptionAPITest {

    /**
     * post용 상품 추가 이미지 파람
     *
     * @param multiValueMap
     */
    public static void createPackageDescriptionParam(MultiValueMap multiValueMap, int index, PackageDescriptionDTO packageDescription) {
        String prefix = "packageDescriptionList[" + index + "].";
        String filePrefix = "imageFileList[" + index + "].";
        // 상품 추가 입력 옵션 사용 여부
        multiValueMap.set(prefix + "subject", packageDescription.getSubject());
        multiValueMap.set(prefix + "content", packageDescription.getContent());

        List<MultipartFile> fileList = packageDescription.getImageFileList();
        if (CollectionUtils.isEmpty(fileList)) {
            return;
        }
        IntStream
            .range(0, fileList.size())
            .forEach(
                fileIndex -> {
                    multiValueMap.set(prefix + "imageFileList[" + fileIndex + "]", fileList.get(fileIndex).getResource());
                }
            );
    }
}
