package com.gez.grill.entity;

/**
 * 上传文件结果.
 */
public class UploadFileResult {

	/**
     * 上传是否成功.
     */
    private boolean success;

    /**
     * 文件名称.
     */
    private String fileName;

    /**
     * 服务端路径.
     */
    private String src;

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}

