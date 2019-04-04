package com.pndoo.grown123_new.soap;

import java.io.Serializable;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.google.inject.Singleton;
import com.pndoo.grown123_new.annotations.ComplexSerializableType;
import com.pndoo.grown123_new.dto.base.AuthorComment;
import com.pndoo.grown123_new.dto.base.AuthorReply;
import com.pndoo.grown123_new.dto.base.BookSeletct;
import com.pndoo.grown123_new.dto.base.Courses;
import com.pndoo.grown123_new.dto.base.PaperInfo;
import com.pndoo.grown123_new.dto.base.ReaderComment;
import com.pndoo.grown123_new.dto.base.ReaderJoin;
import com.pndoo.grown123_new.dto.base.TestPaper;
import com.pndoo.grown123_new.dto.base.TestQuestions;
import com.pndoo.grown123_new.dto.base.TextOneBookDirs;
import com.pndoo.grown123_new.dto.base.TextTwoBookDirs;

/**
 * 登录
 * 
 * @author
 * 
 */
@Singleton
public class CommentJson extends java.util.Observable
		implements
			Serializable,
			Observer {

	private String code;
	private String codeInfo;
	private String notice;
	private AuthorComment AuthorComment;
	private ReaderComment ReaderComment;
	private List<AuthorReply> authorReplyLists;
	private List<ReaderJoin> readerJoins;
	
	
	private List<BookSeletct> bookSeletcts;
	private List<TextOneBookDirs> oneBookDirs;
	private List<TextTwoBookDirs> twoBookDirs;
	private List<TestPaper> papers;
	private List<Courses> courses;
	private TestQuestions questions;
	private PaperInfo paperInfo;
	

	public PaperInfo getPaperInfo() {
		return paperInfo;
	}
	public void setPaperInfo(PaperInfo paperInfo) {
		this.paperInfo = paperInfo;
	}
	public TestQuestions getQuestions() {
		return questions;
	}
	public void setQuestions(TestQuestions questions) {
		this.questions = questions;
	}
	
	@ComplexSerializableType(clazz = Courses.class)
	public List<Courses> getCourses() {
		return courses;
	}
	@ComplexSerializableType(clazz = Courses.class)
	public void setCourses(List<Courses> courses) {
		this.courses = courses;
	}
	@ComplexSerializableType(clazz = TestPaper.class)
	public List<TestPaper> getPapers() {
		return papers;
	}
	@ComplexSerializableType(clazz = TestPaper.class)
	public void setPapers(List<TestPaper> papers) {
		this.papers = papers;
	}
	@ComplexSerializableType(clazz = TextTwoBookDirs.class)
	public List<TextTwoBookDirs> getTwoBookDirs() {
		return twoBookDirs;
	}
	@ComplexSerializableType(clazz = TextTwoBookDirs.class)
	public void setTwoBookDirs(List<TextTwoBookDirs> twoBookDirs) {
		this.twoBookDirs = twoBookDirs;
	}
	@ComplexSerializableType(clazz = TextOneBookDirs.class)
	public List<TextOneBookDirs> getOneBookDirs() {
		return oneBookDirs;
	}
	@ComplexSerializableType(clazz = TextOneBookDirs.class)
	public void setOneBookDirs(List<TextOneBookDirs> oneBookDirs) {
		this.oneBookDirs = oneBookDirs;
	}
	@ComplexSerializableType(clazz = BookSeletct.class)
	public List<BookSeletct> getBookSeletcts() {
		return bookSeletcts;
	}
	@ComplexSerializableType(clazz = BookSeletct.class)
	public void setBookSeletcts(List<BookSeletct> bookSeletcts) {
		this.bookSeletcts = bookSeletcts;
	}
	@ComplexSerializableType(clazz = ReaderJoin.class)
	public List<ReaderJoin> getReaderJoins() {
		return readerJoins;
	}
	@ComplexSerializableType(clazz = ReaderJoin.class)
	public void setReaderJoins(List<ReaderJoin> readerJoins) {
		this.readerJoins = readerJoins;
	}
	@ComplexSerializableType(clazz = AuthorReply.class)
	public List<AuthorReply> getAuthorReplyLists() {
		return authorReplyLists;
	}
	@ComplexSerializableType(clazz = AuthorReply.class)
	public void setAuthorReplyLists(List<AuthorReply> authorReplyLists) {
		this.authorReplyLists = authorReplyLists;
	}

	public AuthorComment getAuthorComment() {
		return AuthorComment;
	}
	public void setAuthorComment(AuthorComment authorComment) {
		AuthorComment = authorComment;
	}
	public ReaderComment getReaderComment() {
		return ReaderComment;
	}
	public void setReaderComment(ReaderComment readerComment) {
		ReaderComment = readerComment;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCodeInfo() {
		return codeInfo;
	}
	public void setCodeInfo(String codeInfo) {
		this.codeInfo = codeInfo;
	}

	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}

	@Override
	public void update(Observable observable, Object data) {
		setChanged();
		notifyObservers();
	}

}
