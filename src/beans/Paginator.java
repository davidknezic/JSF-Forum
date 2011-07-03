package beans;

/**
 * Manages the pagination, provides previous and next page number and generates
 * the entry offset.
 *
 * @author David Knezic <davidknezic@gmail.com>
 */
public class Paginator {

	private int entriesPerPage;
	private int entryCount;
	private int currentPage;

	private int pageCount;

	public Paginator(int entriesPerPage, int entryCount) {
		this.entriesPerPage = entriesPerPage;
		this.entryCount = entryCount;

		// Calculate page count
		this.pageCount = this.entryCount / this.entriesPerPage;
		if (this.entryCount % this.entriesPerPage != 0) {
			this.pageCount++;
		}
	}

	public int getEntriesPerPage() {
		return entriesPerPage;
	}

	public int getEntryCount() {
		return entryCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = _lowerPageBound(currentPage);
		this.currentPage = _upperPageBound(currentPage);
	}

	public int getPageCount() {
		return pageCount;
	}

	public int generateOffset() {
		return this.currentPage * this.entriesPerPage;
	}

	public boolean hasPrev() {
		return (this.currentPage > 0);
	}

	public boolean hasNext() {
		return (this.currentPage + 1 < this.pageCount);
	}

	public int getPrev() {
		return _lowerPageBound(this.currentPage - 1);
	}

	public int getNext() {
		return _upperPageBound(this.currentPage + 1);
	}

	private int _lowerPageBound(int page) {
		return (page >= 0 ? page : 0);
	}

	private int _upperPageBound(int page) {
		// FIX: No pages
		if (this.pageCount == 0) return 0;
		return (page < this.pageCount ? page : this.pageCount - 1);
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(String.format("entriesPerPage: %d\n", this.entriesPerPage));
		b.append(String.format("entryCount: %d\n", this.entryCount));
		b.append(String.format("currentPage: %d\n", this.currentPage));
		b.append(String.format("pageCount: %d\n", this.pageCount));
		return b.toString();
	}

}
