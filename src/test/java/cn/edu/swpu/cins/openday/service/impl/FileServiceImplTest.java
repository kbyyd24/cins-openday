package cn.edu.swpu.cins.openday.service.impl;

import cn.edu.swpu.cins.openday.enums.service.MatchServiceResultEnum;
import cn.edu.swpu.cins.openday.service.FileService;
import cn.edu.swpu.cins.openday.service.TimeService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.charset.Charset;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FileServiceImplTest {

	@Mock
	private TimeService timeService;

	private FileService fileService;

	@Before
	public void setUp() throws Exception {
		fileService = new FileServiceImpl(timeService);
	}

	@Test
	public void test_saveFile_success() throws Exception {
		int registId = 1;
		MultipartFile file = mock(MultipartFile.class);
		String date = "date";
		when(timeService.getDate()).thenReturn(date);
		doNothing().when(file).transferTo(any(File.class));
		assertThat(fileService.saveFile(file, registId), is(MatchServiceResultEnum.SAVE_SUCCESS));
		verify(timeService).getDate();
		verify(file).transferTo(any(File.class));
	}

	@Test
	@Ignore
	public void test_saveFile_with_realObject() throws Exception {
		int registId = 1;
		MultipartFile file =
			new MockMultipartFile("testFile", "test content2".getBytes(Charset.forName("UTF-8")));
		String date = "date";
		when(timeService.getDate()).thenReturn(date);
		assertThat(fileService.saveFile(file, registId), is(MatchServiceResultEnum.SAVE_SUCCESS));
		verify(timeService).getDate();
	}
}