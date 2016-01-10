package frontend.utils.gui;

import java.util.List;

import javax.swing.DefaultComboBoxModel;

import backend.POJOs.Member;

@SuppressWarnings("serial")
public class PostLoadableMemberComboBoxModel extends DefaultComboBoxModel<Member> {

	@Override
	public Member getSelectedItem() {
		return (Member) super.getSelectedItem();
	}
	
	public void loadData(List<Member> members) {
		super.removeAllElements();
		for (Member member : members) {
			super.addElement(member);
		}
	}
	
	public void selectItemByID(int memberID) {
		int i = 0;
		while (i < super.getSize()) {
			if (super.getElementAt(i).getMemberID() == memberID) {
				super.setSelectedItem(super.getElementAt(i));
				break;
			}
			i++;
		}
	}

}
