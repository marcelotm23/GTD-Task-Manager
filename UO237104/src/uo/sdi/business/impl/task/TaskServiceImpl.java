package uo.sdi.business.impl.task;

import java.util.List;

import uo.sdi.business.TaskService;
import uo.sdi.business.exception.BusinessException;
import uo.sdi.business.impl.command.Command;
import uo.sdi.business.impl.command.CommandExecutor;
import uo.sdi.business.impl.task.command.CreateCategoryCommand;
import uo.sdi.business.impl.task.command.CreateTaskCommand;
import uo.sdi.business.impl.task.command.DeleteCategoryCommand;
import uo.sdi.business.impl.task.command.DuplicateCategoryCommand;
import uo.sdi.business.impl.task.command.MarkTaskAsFinishedCommand;
import uo.sdi.business.impl.task.command.UpdateCategoryCommand;
import uo.sdi.business.impl.task.command.UpdateTaskCommand;
import uo.sdi.dto.Category;
import uo.sdi.dto.Task;
import uo.sdi.persistence.Persistence;

public class TaskServiceImpl implements TaskService {

	@Override
	public Long createCategory(Category category) throws BusinessException {
		return new CommandExecutor<Long>().execute(new CreateCategoryCommand(
				category));
	}

	@Override
	public Long duplicateCategory(Long id) throws BusinessException {
		return new CommandExecutor<Long>()
				.execute(new DuplicateCategoryCommand(id));
	}

	@Override
	public void updateCategory(Category category) throws BusinessException {
		new CommandExecutor<Void>()
				.execute(new UpdateCategoryCommand(category));
	}

	@Override
	public void deleteCategory(Long catId) throws BusinessException {
		new CommandExecutor<Void>().execute(new DeleteCategoryCommand(catId));
	}

	@Override
	public Category findCategoryById(final Long id) throws BusinessException {
		return new CommandExecutor<Category>().execute(new Command<Category>() {
			@Override
			public Category execute() throws BusinessException {

				return Persistence.getCategoryDao().findById(id);
			}
		});
	}

	@Override
	public List<Category> findCategoriesByUserId(final Long id)
			throws BusinessException {
		return new CommandExecutor<List<Category>>()
				.execute(new Command<List<Category>>() {
					@Override
					public List<Category> execute() throws BusinessException {

						return Persistence.getCategoryDao().findByUserId(id);
					}
				});
	}

	@Override
	public Long createTask(Task task) throws BusinessException {
		return new CommandExecutor<Long>().execute(new CreateTaskCommand(task));
	}

	@Override
	public void deleteTask(final Long id) throws BusinessException {
		new CommandExecutor<Void>().execute(new Command<Void>() {
			@Override
			public Void execute() throws BusinessException {
				Persistence.getTaskDao().delete(id);
				return null;
			}
		});
	}

	@Override
	public void markTaskAsFinished(Long id) throws BusinessException {
		new CommandExecutor<Void>().execute(new MarkTaskAsFinishedCommand(id));
	}

	@Override
	public void updateTask(Task task) throws BusinessException {
		new CommandExecutor<Void>().execute(new UpdateTaskCommand(task));
	}

	@Override
	public Task findTaskById(final Long id) throws BusinessException {
		return new CommandExecutor<Task>().execute(new Command<Task>() {
			@Override
			public Task execute() throws BusinessException {

				return Persistence.getTaskDao().findById(id);
			}
		});
	}

	@Override
	public List<Task> findInboxTasksByUserId(final Long id)
			throws BusinessException {
		return new CommandExecutor<List<Task>>()
				.execute(new Command<List<Task>>() {
					@Override
					public List<Task> execute() throws BusinessException {

						return Persistence.getTaskDao().findInboxTasksByUserId(
								id);
					}
				});
	}

	@Override
	public List<Task> findWeekTasksByUserId(final Long id)
			throws BusinessException {
		return new CommandExecutor<List<Task>>()
				.execute(new Command<List<Task>>() {
					@Override
					public List<Task> execute() throws BusinessException {

						return Persistence.getTaskDao().findWeekTasksByUserId(
								id);
					}
				});
	}

	@Override
	public List<Task> findTodayTasksByUserId(final Long id)
			throws BusinessException {
		return new CommandExecutor<List<Task>>()
				.execute(new Command<List<Task>>() {
					@Override
					public List<Task> execute() throws BusinessException {

						return Persistence.getTaskDao().findTodayTasksByUserId(
								id);
					}
				});
	}

	@Override
	public List<Task> findTasksByCategoryId(final Long id)
			throws BusinessException {
		return new CommandExecutor<List<Task>>()
				.execute(new Command<List<Task>>() {
					@Override
					public List<Task> execute() throws BusinessException {

						return Persistence.getTaskDao().findTasksByCategoryId(
								id);
					}
				});
	}

	@Override
	public List<Task> findFinishedTasksByCategoryId(final Long id)
			throws BusinessException {
		return new CommandExecutor<List<Task>>()
				.execute(new Command<List<Task>>() {
					@Override
					public List<Task> execute() throws BusinessException {

						return Persistence.getTaskDao()
								.findFinishedTasksByCategoryId(id);
					}
				});
	}

	@Override
	public List<Task> findFinishedInboxTasksByUserId(final Long id)
			throws BusinessException {
		return new CommandExecutor<List<Task>>()
				.execute(new Command<List<Task>>() {
					@Override
					public List<Task> execute() throws BusinessException {

						return Persistence.getTaskDao()
								.findFinishedTasksInboxByUserId(id);
					}
				});
	}

}
