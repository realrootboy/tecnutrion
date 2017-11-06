package dao;

//fk_cod_cliente int
//fk_cod_pedido int
//fk_cod_produto int
//quantidade int
//pedido_item_vl_tot decimal

public class Pedido_itemDAO extends Sql{

	public static void create(String username, String password, Pedido_item pi){
		Connection con = null;
		PreparedStatement stmt = null;
		try{
			con = getConnection(username,password);
			stmt = con.prepareStatement("INSERT INTO pedido_item VALUES (?,?,?,?,?);")
			stmt.setInt(1,pi.getFk_cod_cliente());
			stmt.setInt(2,pi.getFk_cod_pedido());
			stmt.setInt(3,pi.getFk_cod_produto());
			stmt.setInt(4,pi.getQuantidade());
			stmt.setFloat(5,pi.getPedido_item_vl_tot());
			stmt.executeUpdate();
		}catch(Exception e){
			System.out.println(e);
		}finally{
			closeConnection(con,stmt);
		}
	}
	public static ArrayList<Pedido_item> read(String username, String password){
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		ArrayList<Pedido_item> pedidos = new ArrayList<>();

		try{
			con = getConnection(username,password);
			stmt = con.prepareStatement("SELECT * FROM pedido_item");
			rs = stmt.executeQuery();

			while(rs.next()){
				Pedido_item pi = new Pedido_item();
				pi.setFk_cod_cliente(rs.getInt("fk_cod_cliente"));
				pi.setFk_cod_pedido(rs.getInt("fk_cod_pedido"));
				pi.setFk_cod_produto(rs.getInt("fk_cod_produto"));
				pi.setQuantidade(rs.getInt("quantidade"));
				pi.setPedido_item_vl_tot(rs.getFloat("pedido_item_vl_tot"));
				pedidos.add(pi);
			}
		}catch(Exception e){
			System.out.println(e);
		}finally{
			closeConnection(con,stmt,rs);
		}

		return pedidos;
	}
	public static void update(String username,String password, Pedido_item pi){
		Connection con = null;
		PreparedStatement stmt = null;
		try{
			con = getConnection(username,password);
			stmt = con.prepareStatement("UPDATE pedido_item SET fk_cod_cliente=?,fk_cod_pedido=?,fk_cod_produto=?,quantidade=?,pedido_item_vl_tot=? WHERE fk_cod_cliente=?,fk_cod_pedido=?,fk_cod_produto=?");
			stmt.setInt(1,pi.getFk_cod_cliente());
			stmt.setInt(2,pi.getFk_cod_pedido());
			stmt.setInt(3,pi.getFk_cod_produto());
			stmt.setInt(4,pi.getQuantidade());
			stmt.setFloat(5,pi.getPedido_item_vl_tot());
			stmt.setInt(6,pi.getFk_cod_cliente());
			stmt.setInt(7,pi.getFk_cod_pedido());
			stmt.setInt(8,pi.getFk_cod_produto());
			stmt.executeUpdate();
		}catch(Exception e){
			System.out.println(e);
		}finally{
			closeConnection(con,stmt);
		}
	}
	public static void delete(String username, String password, Pedido_item pi, byte mode){
		Connection con = null;
		PreparedStatement stmt = null;
		if(mode==1){
			try{
				con = getConnection(username,password);
				stmt = con.prepareStatement("DELETE FROM pedido_item WHERE fk_cod_cliente=?,fk_cod_pedido=?");
				stmt.setInt(1,pi.getFk_cod_cliente());
				stmt.setInt(2,pi.getFk_cod_pedido());
				stmt.executeUpdate();
			}catch(Exception e){
				System.out.println(e);
			}finally{
				closeConnection(con,stmt);
			}
		}else{
			try{
				con = getConnection(username,password);
				stmt = con.prepareStatement("DELETE FROM pedido_item WHERE fk_cod_cliente=?,fk_cod_pedido=?,fk_cod_produto");
				stmt.setInt(1,pi.getFk_cod_cliente());
				stmt.setInt(2,pi.getFk_cod_pedido());
				stmt.setInt(3,pi.getFk_cod_produto());
				stmt.executeUpdate();
			}catch(Exception e){
				System.out.println(e);
			}finally{
				closeConnection(con,stmt);
			}
		}
	}

}