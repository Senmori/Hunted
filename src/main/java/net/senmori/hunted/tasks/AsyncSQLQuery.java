package net.senmori.hunted.tasks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.senmori.hunted.Hunted;
import net.senmori.hunted.sql.Database;
import net.senmori.hunted.util.LogHandler;

import org.bukkit.scheduler.BukkitRunnable;

public class AsyncSQLQuery extends BukkitRunnable {

	private final String query;
	private final Hunted plugin;
	private final Database db;
	private Connection conn = null;
	private ResultSet resultSet = null;
	private PreparedStatement ps = null;
	private List<Map<String, Object>> results;
	private boolean complete;
	private boolean expectResults;

	public AsyncSQLQuery(Hunted plugin, String query, Database db, boolean expectResults) {
		this.plugin = plugin;
		this.query = query;
		this.db = db;
		this.results = new ArrayList<Map<String, Object>>();
		this.expectResults = expectResults;
		complete = false;
	}

	public void executeTask() {
		this.runTaskAsynchronously(plugin);
	}

	@Override
	public void run() {
		try {
			conn = db.getConnection();
			ps = conn.prepareStatement(query);
			if (expectResults) {
				resultSet = ps.executeQuery();
			} else {
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			LogHandler.dbWarning("Error State: " + e.getSQLState());
			LogHandler.dbWarning("Error Code: " + e.getErrorCode());
			LogHandler.dbWarning("Error message: " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					if (expectResults) {
						getSyncResults();
					}
					resultSet.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				LogHandler.dbWarning("Error State: " + e.getSQLState());
				LogHandler.dbWarning("Error Code: " + e.getErrorCode());
				LogHandler.dbWarning("Error message: " + e.getMessage());
				e.printStackTrace();
			}
			this.cancel();
		}
	}

	private void getSyncResults() {
		if (!expectResults) return;
		new BukkitRunnable() {

			@Override
			public void run() {
				if (resultSet != null) {
					try {
						results = mapResults(resultSet);
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						this.cancel();
					}
				}
			}
		}.runTaskLater(plugin, 1);
		if (results != null) {
			complete = true;
		}
	}

	public boolean isComplete() {
		return complete;
	}

	public List<Map<String, Object>> getResults() {
		return results;
	}

	private List<Map<String, Object>> mapResults(ResultSet set) throws SQLException {
		ResultSetMetaData md = set.getMetaData();
		int columns = md.getColumnCount();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		while (set.next()) {
			HashMap<String, Object> row = new HashMap<String, Object>();
			for (int i = 1; i < columns; i++) {
				row.put(md.getColumnName(i), set.getObject(i));
			}
			list.add(row);
		}
		return list;
	}

}
