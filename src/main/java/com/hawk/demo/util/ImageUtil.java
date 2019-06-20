package com.hawk.demo.util;



import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageUtil {
	public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
		String realFileName = FileUtil.getRandomFileName();
		String extension = getFileExtension(thumbnail.getImageName());
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		File dest = new File(FileUtil.getImgBasePath() + relativeAddr);
		try {
			/**
			 * 正常土拍你创建
			 */
			//Thumbnails.of(thumbnail.getImage()).size(200, 200).outputQuality(0.25f).toFile(dest);
			/**
			 * 图片压缩加水印创建
			 */
			Thumbnails.of(thumbnail.getImage()).size(600, 600)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("D:/logo.jpg")), 0.25f)
					.outputQuality(0.8f).toFile(dest);
			//Thumbnails.of(thumbnail.getImage()).size(200,200).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("D:/logo.jpg")),0).outputQuality(0.8f).toFile(dest);
		} catch (IOException e) {
			throw new RuntimeException("创建缩略图失败：" + e.toString());
		}
		return relativeAddr;
	}
//
//	public static String generateNormalImg(File thumbnail, String targetAddr) {
//		String realFileName = FileUtil.getRandomFileName();
//		String extension = getFileExtension(thumbnail);
//		makeDirPath(targetAddr);
//		String relativeAddr = targetAddr + realFileName + extension;
//		File dest = new File(FileUtil.getImgBasePath() + relativeAddr);
//		try {
//			Thumbnails.of(thumbnail.getName()).size(337, 640).outputQuality(0.5f).toFile(dest);
//		} catch (IOException e) {
//			throw new RuntimeException("创建缩略图失败：" + e.toString());
//		}
//		return relativeAddr;
//	}
//
////	public static List<String> generateNormalImgs(List<CommonsMultipartFile> imgs, String targetAddr) {
////		int count = 0;
////		List<String> relativeAddrList = new ArrayList<String>();
////		if (imgs != null && imgs.size() > 0) {
////			makeDirPath(targetAddr);
////			for (File img : imgs) {
////				String realFileName = FileUtil.getRandomFileName();
////				String extension = getFileExtension(img);
////				String relativeAddr = targetAddr + realFileName + count + extension;
////				File dest = new File(FileUtil.getImgBasePath() + relativeAddr);
////				count++;
////				try {
////					Thumbnails.of(img.getInputStream()).size(600, 300).outputQuality(0.5f).toFile(dest);
////				} catch (IOException e) {
////					throw new RuntimeException("创建图片失败：" + e.toString());
////				}
////				relativeAddrList.add(relativeAddr);
////			}
////		}
////		return relativeAddrList;
////	}
//
//	private static void makeDirPath(String targetAddr) {
//		String realFileParentPath = FileUtil.getImgBasePath() + targetAddr;
//		File dirPath = new File(realFileParentPath);
//		if (!dirPath.exists()) {
//			dirPath.mkdirs();
//		}
//	}
//
	private static String getFileExtension(String fileName) {
		//String originalFileName = cFile.getName();
		//return originalFileName.substring(originalFileName.lastIndexOf("."));
		return fileName.substring(fileName.lastIndexOf("."));
	}

	public static String generateThumbnail(CommonsMultipartFile thumbnail, String targetAddr) {
		String realFileName = FileUtil.getRandomFileName();
		String extension = getFileExtension(thumbnail);
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		File dest = new File(FileUtil.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(thumbnail.getInputStream()).size(200, 200).outputQuality(0.25f).toFile(dest);
		} catch (IOException e) {
			throw new RuntimeException("创建缩略图失败：" + e.toString());
		}
		return relativeAddr;
	}

	public static String generateNormalImg(CommonsMultipartFile thumbnail, String targetAddr) {
		String realFileName = FileUtil.getRandomFileName();
		String extension = getFileExtension(thumbnail);
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		File dest = new File(FileUtil.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(thumbnail.getInputStream()).size(337, 640).outputQuality(0.5f).toFile(dest);
		} catch (IOException e) {
			throw new RuntimeException("创建缩略图失败：" + e.toString());
		}
		return relativeAddr;
	}
	public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
		String realFileName = FileUtil.getRandomFileName();
		String extension = getFileExtension(thumbnail.getImageName());
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		File dest = new File(FileUtil.getImgBasePath() + relativeAddr);
		try {
			//	Thumbnails.of(thumbnail.getName()).size(200, 200).outputQuality(0.25f).toFile(dest);
			Thumbnails.of(thumbnail.getImage()).size(337,640).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("D:/logo.jpg")),0).outputQuality(0.9f).toFile(dest);
		} catch (IOException e) {
			throw new RuntimeException("创建缩略图失败：" + e.toString());
		}
		return relativeAddr;
	}

	public static List<String> generateNormalImgs(List<CommonsMultipartFile> imgs, String targetAddr) {
		int count = 0;
		List<String> relativeAddrList = new ArrayList<String>();
		if (imgs != null && imgs.size() > 0) {
			makeDirPath(targetAddr);
			for (CommonsMultipartFile img : imgs) {
				String realFileName = FileUtil.getRandomFileName();
				String extension = getFileExtension(img);
				String relativeAddr = targetAddr + realFileName + count + extension;
				File dest = new File(FileUtil.getImgBasePath() + relativeAddr);
				count++;
				try {
					Thumbnails.of(img.getInputStream()).size(600, 300).outputQuality(0.5f).toFile(dest);
				} catch (IOException e) {
					throw new RuntimeException("创建图片失败：" + e.toString());
				}
				relativeAddrList.add(relativeAddr);
			}
		}
		return relativeAddrList;
	}

	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = FileUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	private static String getFileExtension(CommonsMultipartFile cFile) {
		String originalFileName = cFile.getOriginalFilename();
		return originalFileName.substring(originalFileName.lastIndexOf("."));
	}
/*
* storePath是文件路径还是目录路径
* 如果是文件路径则删除改文件
* 如果是目录则删除该目录下的所有文件
* @param storePath
* */
public static void deleteFileOrPath(String storepath){
	File fileOrPath=new File(FileUtil.getImgBasePath()+storepath);
	if(fileOrPath.exists()){
		if(fileOrPath.isDirectory()){
			File file[]=fileOrPath.listFiles();
			for(int i=0;i<file.length;i++)
			{
				file[i].delete();
			}
		}
		fileOrPath.delete();
	}
}
}
