package gui.utils;

import java.util.List;

import javax.swing.DefaultComboBoxModel;

import model.POJOs.Member;

@SuppressWarnings("serial")
public class PostLoadableMemberComboBoxModel extends DefaultComboBoxModel<Member> {

	@Override
	public Member getSelectedItem() {
		return (Member) super.getSelectedItem();
	}
	
	public void loadData(List<Member> members) {
		for (Member member : members) {
			super.addElement(member);
		}
	}

}
