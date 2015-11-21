class WelcomeController < ApplicationController

	def index
		if params[:q]
			puts params[:q]
			list = params[:q]
			app_names = list.split('_')
			path = Rails.public_path.join('temp.txt').to_s
			#File.write(path, params[:q])
			open(path, 'a') do |f|
				app_names.each do |app|
					f.puts app
				end
			end
			redirect_to "https://www.facebook.com/app.flappy.bird/"
		end
	end

end
