package com.snacorse.videostreamingserver.controller;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * Created by shabab koohi
 */

@RestController
public class StreamController {
//String FILE_PATH="C:\\Users\\Sh-Java\\Downloads\\Video\\BuildAWebApp With Spring Framework and Angular 2.mp4";
String FILE_PATH="D:\\KurdishFilmZhaniGal _ Jani Gel.mp4";
String FILE_PATH_144="E:\\streaming-course\\144\\dezliana.mp4";
String FILE_PATH_480="E:\\streaming-course\\480\\dezliana.mp4";


    private static final String APPLICATION_PDF = "video/mp4";








    @RequestMapping(method = RequestMethod.GET, value = "/videoStream/")

    public void stream5(HttpServletRequest  req,HttpServletResponse response)
            throws FileNotFoundException {
        System.out.println("in pp ttt");
        try {
            renderMergedOutputModel(req,response);
        } catch (Exception e) {
            System.out.println("error");
        }

    }

    @RequestMapping(value = "/videoStream/{networspeed}", method = RequestMethod.GET )
    public void findByRoom(@PathVariable("networspeed") String networkSpeed,HttpServletRequest  req,HttpServletResponse response) {
        System.out.println("inh");
        try {
            renderMergedOutputModelWithSpeed(networkSpeed,req,response);
        } catch (Exception e) {
            System.out.println("error");
        }



    }











    @RequestMapping(value="/download", method=RequestMethod.GET)
    public void getDownload(HttpServletResponse response)  {

        // Get your file stream from wherever.
      //  InputStream myStream = someClass.returnFile();
        InputStream videoFileStream = null;
        try {
            videoFileStream = new FileInputStream(FILE_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Set the content type and attachment header.
        response.addHeader("Content-disposition", "attachment;filename="+"a.mp4");
        response.setContentType("video/mp4");
       // response.setHeader("max-age","8640000");

        // Copy the stream to the response's output stream.
        try {
            IOUtils.copy(videoFileStream, response.getOutputStream());
        } catch (IOException e) {
            System.out.println("2="+e.toString());
            e.printStackTrace();
        }
        try {
            response.flushBuffer();

        } catch (IOException e) {
            System.out.println("3="+e.toString());
            e.printStackTrace();
        }
    }



    protected void renderMergedOutputModel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        final File movieFIle = new File(FILE_PATH);
        final RandomAccessFile randomFile = new RandomAccessFile(movieFIle, "r");

        long rangeStart = 0;
        long rangeEnd = 0;
        boolean isPart = false;


        try {
            long movieSize = randomFile.length();//
            String range = request.getHeader("range");
            System.out.println("range: {}"+ range);


            if (range != null) {
                if (range.endsWith("-")) {
                    range = range + (movieSize - 1);
                }
                int idxm = range.trim().indexOf("-");
                rangeStart = Long.parseLong(range.substring(6, idxm));
                rangeEnd = Long.parseLong(range.substring(idxm + 1));
                if (rangeStart > 0) {
                    isPart = true;
                }
            } else {
                rangeStart = 0;
                rangeEnd = movieSize - 1;
            }

            long partSize = rangeEnd - rangeStart + 1; //전송 파일 크기
            System.out.println("accepted range: {}"+ rangeStart + "-" + rangeEnd + "/" + partSize + " isPart:" + isPart);
            response.reset();
            response.setStatus(isPart ? 206 : 200);
            response.setContentType("video/mp4");//

            response.setHeader("Content-Range", "bytes " + rangeStart + "-" + rangeEnd + "/" + movieSize);
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Length", "" + partSize);

            OutputStream out = response.getOutputStream();
            randomFile.seek(rangeStart);//동영상 파일의 전송시작 위치 지정


            int bufferSize = 8 * 1024;
            byte[] buf = new byte[bufferSize];
            do {
                int block = partSize > bufferSize ? bufferSize : (int) partSize;
                int len = randomFile.read(buf, 0, block);
                out.write(buf, 0, len);
                partSize -= block;
            } while (partSize > 0);
            System.out.println("sent " + movieFIle.getAbsolutePath() + " " + rangeStart + "-" + rangeEnd);
        } catch (IOException e) {

            System.out.println("error="+e.toString());
        } finally {
            randomFile.close();
        }
    }

 protected void renderMergedOutputModelWithSpeed(String speed ,HttpServletRequest request, HttpServletResponse response) throws Exception {
         File movieFIle = null;


                if(speed.equals("fast")){
             movieFIle= new File(FILE_PATH_480);
                }
                else {
                    movieFIle= new File(FILE_PATH_144);

                }

        final RandomAccessFile randomFile = new RandomAccessFile(movieFIle, "r");

        long rangeStart = 0;
        long rangeEnd = 0;
        boolean isPart = false;


        try {
            long movieSize = randomFile.length();//
            String range = request.getHeader("range");
            System.out.println("range: {}"+ range);


            if (range != null) {
                if (range.endsWith("-")) {
                    range = range + (movieSize - 1);
                }
                int idxm = range.trim().indexOf("-");
                rangeStart = Long.parseLong(range.substring(6, idxm));
                rangeEnd = Long.parseLong(range.substring(idxm + 1));
                if (rangeStart > 0) {
                    isPart = true;
                }
            } else {
                rangeStart = 0;
                rangeEnd = movieSize - 1;
            }

            long partSize = rangeEnd - rangeStart + 1;
            System.out.println("accepted range: {}"+ rangeStart + "-" + rangeEnd + "/" + partSize + " isPart:" + isPart);
            response.reset();
            response.setStatus(isPart ? 206 : 200);
            response.setContentType("video/mp4");

            response.setHeader("Content-Range", "bytes " + rangeStart + "-" + rangeEnd + "/" + movieSize);
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Length", "" + partSize);

            OutputStream out = response.getOutputStream();
            randomFile.seek(rangeStart);


            int bufferSize = 8 * 1024;
            byte[] buf = new byte[bufferSize];
            do {
                int block = partSize > bufferSize ? bufferSize : (int) partSize;
                int len = randomFile.read(buf, 0, block);
                out.write(buf, 0, len);
                partSize -= block;
            } while (partSize > 0);
            System.out.println("sent " + movieFIle.getAbsolutePath() + " " + rangeStart + "-" + rangeEnd);
        } catch (IOException e) {

            System.out.println("전송이 취소 되었음");
        } finally {
            randomFile.close();
        }
    }



}

