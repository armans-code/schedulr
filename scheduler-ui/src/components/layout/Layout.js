import Header from './Header';

function Layout({ children }) {
	return (
		<div className='flex flex-col h-full'>
			<Header />
			<main>{children}</main>
		</div>
	);
}

export default Layout;
