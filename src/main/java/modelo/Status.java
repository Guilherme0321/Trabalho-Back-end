package modelo;

public class Status {
	private String status;
	
	public Status(String status){
		if(status.equals("pendente") || status.equals("confirmada") || status.equals("cancelada")) {
			this.setStatus(status);
		}else {
			System.out.println("Somente 'pendente', 'confirmada', 'cancelada' sao aceitaveis");
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
