package kr.co.uclick.vo;

import java.util.ArrayList;
import java.util.List;

import kr.co.uclick.entity.User;

public class PaginationVO {
// 페이지네이션을 위한 데이터를 주고받기 위한 객체를 정의한 클래스... users 객체도 가질 수 있다.

	private int totalpage = 0;
	private int first = 0;
	private int prev = 0;
	private int start = 0;
	private int end  = 0;
	private int next = 0;
	private int last = 0;
	private List<User> users = new ArrayList<User>();
	
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	public int getTotalpage() {
		return totalpage;
	}
	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
	}
	public int getFirst() {
		return first;
	}
	public void setFirst(int first) {
		this.first = first;
	}
	public int getPrev() {
		return prev;
	}
	public void setPrev(int prev) {
		this.prev = prev;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}	
	public int getNext() {
		return next;
	}
	public void setNext(int next) {
		this.next = next;
	}
	public int getLast() {
		return last;
	}
	public void setLast(int last) {
		this.last = last;
	}
}
