package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.enums.service.MatchServiceResultEnum;
import cn.edu.swpu.cins.openday.exception.FileException;
import cn.edu.swpu.cins.openday.service.FileService;
import cn.edu.swpu.cins.openday.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {
	private TimeService timeService;
	@Value("${openday.answer.location:/opt/openday/answers}")
	private String location;

	@Autowired
	public FileServiceImpl(TimeService timeService) {
		this.timeService = timeService;
	}

	@Override
	public MatchServiceResultEnum saveFile(MultipartFile multipartFile, int registId) {
		String path = checkDir(registId);
		path += "/" + timeService.getDate();
		File file = new File(path);
		try {
			multipartFile.transferTo(file);
		} catch (IOException e) {
			e.printStackTrace();
			return MatchServiceResultEnum.SAVE_FAILED;
		}
		return MatchServiceResultEnum.SAVE_SUCCESS;
	}

	private String checkDir(int registId) {
		String path = location + "/" + registId;
		File dir = new File(path);
		if (!dir.exists()) {
			if (!dir.mkdirs()) {
				throw new FileException("create dir failed");
			}
		}
		return path;
	}
}
